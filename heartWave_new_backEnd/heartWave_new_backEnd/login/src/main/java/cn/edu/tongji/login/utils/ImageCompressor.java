package cn.edu.tongji.login.utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageCompressor {
    public static byte[] compress(byte[] bytesImage, int newWidth, int newHeight, boolean ifChangeWidthHeightRatio) {
        try {
            //将字节数组转换为BufferedImage对象
            ByteArrayInputStream in = new ByteArrayInputStream(bytesImage);
            BufferedImage originImage = ImageIO.read(in);
            in.close();

            int scaledWidth = newWidth;
            int scaledHeight = newHeight;

            //根据是否改变长宽比，计算压缩后的尺寸
            if (!ifChangeWidthHeightRatio) {
                int originWidth = originImage.getWidth();
                int originHeight = originImage.getHeight();
                double scaleX = (double) newWidth / originWidth;
                double scaleY = (double) newHeight / originHeight;
                double scale = Math.min(scaleX, scaleY);

                scaledWidth = (int) (originWidth * scale);
                scaledHeight = (int) (originHeight * scale);
            }

            //创建压缩后的图像
            BufferedImage newImage = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = newImage.createGraphics();
            g2d.drawImage(originImage, 0, 0, scaledWidth, scaledHeight, null);
            g2d.dispose();

            //将压缩后图像转换为base64编码
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(newImage, "jpg", out);
            byte[] newBytes = out.toByteArray();
            out.close();
            return newBytes;
        } catch (IOException e) {
            e.printStackTrace();
            return bytesImage;
        }
    }
}
