import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

// 레이턴시 편.
public class _01_ImageColorChange {

    public static final String SOURCE_FILE = "_03_optimizing/resources/many-flowers.jpg";
    public static final String DESTINATION_FILE = "./out/many-flowers.jpg";

    public static void main(String[] args) throws IOException {
        BufferedImage originalImage = ImageIO.read(new File(SOURCE_FILE));
        BufferedImage resultImage = new BufferedImage(originalImage.getWidth(),
              originalImage.getHeight(), BufferedImage.TYPE_INT_RGB);

        int numberOfThreads = 10;
        long start = System.currentTimeMillis();
        recolorMultiThreaded(originalImage, resultImage, numberOfThreads);
        long end = System.currentTimeMillis();

        File outputFile = new File(DESTINATION_FILE);
        ImageIO.write(resultImage, "jpg", outputFile);

        System.out.println("single duration = " + (end - start));
    }

    public static void recolorSingleThreaded(BufferedImage originalImage,
          BufferedImage resultImage) {
        recolorImage(originalImage, resultImage, 0, 0, originalImage.getWidth(),
              originalImage.getHeight());
    }

    public static void recolorMultiThreaded(BufferedImage originalImage,
          BufferedImage resultImage, int numberOfThreads) {

        List<Thread> threadList = new ArrayList<>();
        int width = originalImage.getWidth();
        int height = originalImage.getHeight() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            final int threadMultiplier = i;

            Thread thread = new Thread(() -> {
                int leftCorner = 0;
                int topCorner = height * threadMultiplier;

                recolorImage(originalImage, resultImage, leftCorner, topCorner, width, height);
            });

            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }

        for (Thread thread : threadList) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void recolorImage(BufferedImage originalImage, BufferedImage resultImage,
          int leftCorner, int topCorner, int width, int height) {
        for (int x = leftCorner; x < leftCorner + width && x < originalImage.getWidth(); x++) {
            for (int y = topCorner; y < topCorner + height && y < originalImage.getHeight(); y++) {
                recolorPixel(originalImage, resultImage, x, y);
            }
        }
    }

    public static void recolorPixel(BufferedImage originalImage, BufferedImage resultImage, int x, int y) {
        int rgb = originalImage.getRGB(x, y);

        int red = getRed(rgb);
        int green = getGreen(rgb);
        int blue = getBlue(rgb);

        int newRed, newGreen, newBlue;

        // 흰색인 꽃들을 보라색으로 변경 (원하는 색상에 맞게 숫자를 변경해나가면 됨.)
        if (isShadeOfGray(red, green, blue)) {
            newRed = Math.min(255, red + 10);
            newGreen = Math.max(0, green - 80);
            newBlue = Math.max(0, blue - 20);
        } else {
            newRed = red;
            newGreen = green;
            newBlue = blue;
        }

        int newRGB = createARGBFromColors(newRed, newGreen, newBlue);
        setRGB(resultImage, x, y, newRGB);
    }

    public static void setRGB(BufferedImage image, int x, int y, int rgb) {
        image.getRaster().setDataElements(x, y, image.getColorModel().getDataElements(rgb, null));
    }

    public static boolean isShadeOfGray(int red, int green, int blue) {
        return Math.abs(red - green) < 30
              && Math.abs(red - blue) < 30
              && Math.abs(green - blue) < 30;
    }

    public static int createARGBFromColors(int red, int green, int blue) {
        int rgb = 0;

        // ARGB 표현 방식 자체가 비트마스크 형태임.
        rgb |= blue;
        rgb |= green << 8;
        rgb |= red << 16;

        // 가장 앞에 Alpha(픽셀 투명성 값)이 붙는다.
        rgb |= 0xFF000000;

        return rgb;
    }
    public static int getRed(int rgb) {
        return (rgb & 0x00FF0000) >> 16;
    }

    public static int getGreen(int rgb) {
        return (rgb & 0x0000FF00) >> 8;
    }

    public static int getBlue(int rgb) {
        return rgb & 0x000000FF;
    }
}
