package cn.edu.tongji.login.utils;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class RandomVerifyCode {
    private final static String VERIFY_CODE = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    private final static Random random = new Random();
    private static BufferedImage img;
    private static int imgWidth = 150;
    private static int imgHeight = 75;

    public static String getVerifyCode(int size) {
        return getVerifyCode(size, VERIFY_CODE);
    }
    public static String getVerifyCode(int size, String src) {
        if (src == null || src.isEmpty()) {
            src = VERIFY_CODE;
        }
        int codesLen = src.length();
        int maxIndex = codesLen - 1;
        StringBuilder verifyCode = new StringBuilder(size);
        for (int i = 0; i < size; i++) {
            verifyCode.append(src.charAt(random.nextInt(maxIndex)));
        }
        return verifyCode.toString();
    }
    public static String getVerifyImage(int w, int h, File outputFile, int size) throws IOException {
        String verifyCode = getVerifyCode(size);
        outputImage(w, h, outputFile, verifyCode);
        return verifyCode;
    }
    public static String getVerifyImage(int w, int h, OutputStream os, int size) throws IOException {
        String verifyCode = getVerifyCode(size);
        outputImage(w, h, os, verifyCode);
        return verifyCode;
    }

    public static void outputImage(int w, int h, File outputFile, String verifyCode) throws IOException {
        if (outputFile == null) {
            return;
        }
        Path outputFilePath = outputFile.toPath();
        Path outputDir = outputFilePath.getParent();

        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            outputImage(w, h, fos, verifyCode);
        }
    }

    public static String base64StringOfCode(String code) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        outputImage(imgWidth, imgHeight, byteArrayOutputStream, code);
        byte[] imageAsBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(imageAsBytes);
    }
    private static Color getRandColor(int fc, int bc) {
        return new Color(
                fc + random.nextInt(Math.max(bc - fc + 1, 0)),
                fc + random.nextInt(Math.max(bc - fc + 1, 0)),
                fc + random.nextInt(Math.max(bc - fc + 1, 0))
        );
    }
    private static int getRandomIntColor() {
        int[] rgb = getRandomRgb();
        int color = 0;
        for (int c : rgb) {
            color = (color << 8) | (c & 0xFF);
        }
        return color;
    }

    private static int[] getRandomRgb() {
        return new int[] {
                random.nextInt(256),
                random.nextInt(256),
                random.nextInt(256),
        };
    }
    private static void shear(Graphics g, int w1, int h1, Color color) {
        shearX(g, w1, h1, color);
        shearY(g, w1, h1, color);
    }

    private static void shearY(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(40) + 10;
        boolean borderGap = true;
        int frames = 20;
        int phase = 7;
        for (int i = 0; i < w1; i++) {
            double d = (period >> 1) * Math.sin((double) i / period + (6.2831853071795862D * phase) / frames);
            g.copyArea(i, 0, 1, h1, 0, (int) d);
            if (borderGap) {
                g.setColor(color);
                g.drawLine(i, (int) d, i, 0);
                g.drawLine(i, (int) d + h1, i, h1);
            }
        }
    }

    private static void shearX(Graphics g, int w1, int h1, Color color) {
        int period = random.nextInt(2);
        boolean borderGap = true;
        int frames = 1;
        int phase = random.nextInt(2);

        for (int i = 0; i < h1; i++) {
            double d = (period >> 1) * Math.sin((double) i / period + (6.2831853071795862D * phase) / frames);
            g.copyArea(0, i, w1, 1, (int) d, 0);
            if (borderGap) {
                g.setColor(color);
                g.drawLine((int) d, i, 0, i);
                g.drawLine((int) d + w1, i, w1, i);
            }
        }
    }
    public static void outputImage(int w, int h, OutputStream os, String code) throws IOException {
        int size = code.length();
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        Graphics2D graphics2D = image.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        Color[] colorsSpaces = new Color[] {
                Color.WHITE,
                Color.CYAN,
                Color.GRAY,
                Color.LIGHT_GRAY,
                Color.MAGENTA,
                Color.ORANGE,
                Color.PINK,
                Color.YELLOW,
        };
        Color[] colors = Arrays.copyOf(colorsSpaces, 5);
        float[] fractions = Arrays.copyOf(generateFractions(rand), colors.length);
        Arrays.sort(fractions);
        graphics2D.setColor(Color.GRAY);
        graphics2D.fillRect(0, 0, w, h);
        Color bgColor = getRandColor(200, 250);
        graphics2D.setColor(bgColor);
        graphics2D.fillRect(0, 2, 2, h - 4);

        if (img == null) {
            loadImg();
        }
        AffineTransform bgAffine = new AffineTransform();
        bgAffine.setToScale((double) w / imgWidth, (double) h / imgHeight);
        graphics2D.drawImage(img, bgAffine, null);

        int fontSize = h - 4;
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        graphics2D.setFont(font);
        char[] chars = code.toCharArray();
        for (int i = 0; i < size; i++) {
            graphics2D.setColor(getRandColor(100, 160));
            AffineTransform affine = new AffineTransform();
            affine.setToRotation(Math.PI / 8 * rand.nextDouble() * (rand.nextBoolean() ? 1 : -1), (w / size) * i + fontSize / 2, h / 2);
            graphics2D.setTransform(affine);
            graphics2D.drawChars(chars, i, 1, ((w - 30) / size) * i - 10, h / 2 + fontSize / 2 - 10);
        }

        int lineAmount = 40;
        graphics2D.setColor(getRandColor(160, 200));
        drawInterferenceLines(graphics2D, w, h, lineAmount);

        float yawpRate = 0.05f;
        addNoise(image, yawpRate);

        graphics2D.dispose();
        ImageIO.write(image, "png", os);
    }
    private static float[] generateFractions(Random rand) {
        float[] fractions = new float[5];
        for (int i = 0; i < fractions.length; i++) {
            fractions[i] = rand.nextFloat();
        }
        return fractions;
    }

    private static void loadImg() throws IOException {
        ClassPathResource classPathResource = new ClassPathResource("static/img/bg.png");
        try (InputStream inputStreamImg = classPathResource.getInputStream()) {
            img = ImageIO.read(inputStreamImg);
            imgWidth = img.getWidth();
            imgHeight = img.getHeight();
        }
    }
    private static void drawInterferenceLines(Graphics2D g2, int w, int h, int lineAmount) {
        Random random = new Random();
        for (int i = 0; i < lineAmount; i++) {
            int x = random.nextInt(w - 1);
            int y = random.nextInt(h - 1);
            int xl = random.nextInt(6) + 1;
            int yl = random.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }
    }

    private static void addNoise(BufferedImage image, float yawpRate) {
        int area = (int) (yawpRate * image.getWidth() * image.getHeight() * 3);
        Random random = new Random();
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(image.getWidth());
            int y = random.nextInt(image.getHeight());
            int rgb = getRandomIntColor();
            image.setRGB(x, y, rgb);
        }
    }

    public static void main(String[] args) throws IOException {
        File dir = new File("./");
        int w = 200, h = 80;
        String verifyCode = getVerifyCode(4);
        File file = new File(dir, verifyCode + ".jpg");
        outputImage(w, h, file, verifyCode);
    }
}
