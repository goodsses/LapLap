package com.sh;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

public class TestImage {

    @Test
    public void draw() {
        String imagePath = "C:\\Users\\NaNa\\Desktop\\QrCode.png";
        String path = "C:\\Users\\NaNa\\Desktop\\ok.png";
        String content = "123123";
        //读取图片文件，得到BufferedImage对象
        BufferedImage bimg;
        try {
            bimg = ImageIO.read(new FileInputStream(imagePath));
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //得到Graphics2D 对象
            Graphics2D g2d = (Graphics2D) bimg.getGraphics();
            //设置颜色和画笔粗细
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(3));
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 23));
            //绘制图案或文字
            g2d.drawString(content, 215, 510);
            //保存新图片
            ImageIO.write(bimg, "JPG", out);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentType(MediaType.IMAGE_JPEG);
            new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
