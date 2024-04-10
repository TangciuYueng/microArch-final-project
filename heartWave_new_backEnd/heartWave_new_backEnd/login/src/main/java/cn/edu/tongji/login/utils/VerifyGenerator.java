package cn.edu.tongji.login.utils;

import org.springframework.core.io.ClassPathResource;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Random;

public class VerifyGenerator {
    //易混淆：
    //  1、l、I、
    //  0、O、o
    private static final String ALPHABET = "23456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz";
    private static BufferedImage logo;
    private static final int LOGO_WIDTH = 200;
    private static final int LOGO_HEIGHT = 200;
    private static final int IMAGE_WIDTH = 400;
    private static final int IMAGE_HEIGHT = 200;

    public static String getVerifyCode(final int length) {
        Random generator = new Random(System.currentTimeMillis());
        StringBuilder verifyCode = new StringBuilder();

        for (int i = 0; i < length; i++) {
            verifyCode.append(ALPHABET.charAt(generator.nextInt(ALPHABET.length() - 1)));
        }

        return verifyCode.toString();
    }

    private static Color getRandColor(int fgColor, int bgColor) {
        Random generator = new Random();
        fgColor = Math.min(fgColor, 255);
        fgColor = Math.max(fgColor, 0);
        bgColor = Math.min(bgColor, 255);
        bgColor = Math.max(bgColor, 0);

        return new Color(
                fgColor + generator.nextInt(bgColor - fgColor),
                fgColor + generator.nextInt(bgColor - fgColor),
                fgColor + generator.nextInt(bgColor - fgColor)
        );
    }

    private static int getRandIntColor() {
        Random generator = new Random();
        int randColor = 0;

        for (int i = 0; i < 3; i++) {
            randColor = randColor << 8;
            randColor = randColor | generator.nextInt(256);
        }

        return randColor;
    }

    private static void drawVerifyImageBackground(Graphics2D g2, final int width, final int height) {
        AffineTransform scaleTransformer = new AffineTransform();
        scaleTransformer.setToScale((double) width / LOGO_WIDTH, (double) height / LOGO_HEIGHT);
        g2.drawImage(logo, scaleTransformer, null);
    }

    private static void drawVerifyImageCode(Graphics2D g2, final int width, final int height, final String verifyCode) {
        int fontSize = height - 4;
        int codeLength = verifyCode.length();
        Random generator = new Random();
        Font font = new Font("Algerian", Font.ITALIC, fontSize);
        g2.setFont(font);

        for (int i = 0; i < codeLength; i++) {
            g2.setColor(getRandColor(100, 160));

            AffineTransform rotateTransformer = new AffineTransform();
            rotateTransformer.setToRotation(
                    Math.PI / 8 * generator.nextDouble() * (generator.nextBoolean() ? 1 : -1),
                    ((double) width / codeLength) * i + (double) fontSize / 2,
                    (double) height / 2
            );
            g2.setTransform(rotateTransformer);

            g2.drawChars(
                    verifyCode.toCharArray(),
                    i,
                    1,
                    ((width - 30) / codeLength) * i - 10,
                    height / 2 + fontSize / 2 - 10
            );
        }
    }

    private static void drawVerifyImageLine(Graphics2D g2, final int width, final int height, final int lineCount) {
        Random generator = new Random();
        g2.setColor(getRandColor(160, 200));// 设置线条的颜色

        for (int i = 0; i < lineCount; i++) {
            int x = generator.nextInt(width - 1);
            int y = generator.nextInt(height - 1);
            int xl = generator.nextInt(6) + 1;
            int yl = generator.nextInt(12) + 1;
            g2.drawLine(x, y, x + xl + 40, y + yl + 20);
        }
    }

    private static void drawVerifyImageNoise(final int width, final int height, final float rate, BufferedImage image) {
        Random generator = new Random();
        int noiseArea = (int) (rate * width * height);

        for (int i = 0; i < noiseArea * 3; i++) {
            int x = generator.nextInt(width);
            int y = generator.nextInt(height);
            int rgb = getRandIntColor();
            image.setRGB(x, y, rgb);
        }
    }

    private static void generateVerifyImage(String verifyCode, OutputStream out) {
        try {
            BufferedImage image = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = image.createGraphics();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.GRAY);// 设置边框色
            g2.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);

            Color c = getRandColor(200, 250);
            g2.setColor(c);// 设置背景色
            g2.fillRect(0, 2, IMAGE_WIDTH, IMAGE_HEIGHT - 4);

            if (logo == null) {
                ClassPathResource classPathResource = new ClassPathResource("static/img/music.png");
                InputStream inFromLogo = classPathResource.getInputStream();
                logo = ImageIO.read(inFromLogo);
            }

            drawVerifyImageBackground(g2, IMAGE_WIDTH, IMAGE_HEIGHT);                //绘制背景
            drawVerifyImageCode(g2, IMAGE_WIDTH, IMAGE_HEIGHT, verifyCode);          //绘制验证码
            drawVerifyImageLine(g2, IMAGE_WIDTH, IMAGE_HEIGHT, 40);         //绘制干扰线
            drawVerifyImageNoise(IMAGE_WIDTH, IMAGE_HEIGHT, 0.01f, image);  //添加噪点
            g2.dispose();  //释放绘图资源

            ImageIO.write(image, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateVerifyImage(String verifyCode) {
        //image支持写入到输出流，因此写入后再转换成byte[]，最后编码成base64
        ByteArrayOutputStream outToMedium = new ByteArrayOutputStream();
        generateVerifyImage(verifyCode, outToMedium);
        return Base64.getEncoder().encodeToString(outToMedium.toByteArray());
    }

    public static void main(String[] args) {
        try {
            String verifyCode = getVerifyCode(4);
            File image = new File("C:/Users/CHEMISTRYMASTER/Desktop/" + verifyCode + ".png");
            generateVerifyImage(verifyCode, new FileOutputStream(image));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
