package com.sh.controller;

import com.alibaba.fastjson.JSON;
import com.sh.ctrl.entity.Dealers;
import com.sh.ctrl.entity.Models;
import com.sh.ctrl.entity.QuestionBank;
import com.sh.ctrl.service.DealersService;
import com.sh.ctrl.service.ModelsService;
import com.sh.ctrl.service.QuestionBankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

@Controller
@RequestMapping("web")
public class WebController {

    private QuestionBankService questionBankService;

    private DealersService dealersService;

    private ModelsService modelsService;

    @Autowired
    public void setModelsService(ModelsService modelsService) {
        this.modelsService = modelsService;
    }

    @Autowired
    public void setQuestionBankService(QuestionBankService questionBankService) {
        this.questionBankService = questionBankService;
    }

    @Autowired
    public void setDealersService(DealersService dealersService) {
        this.dealersService = dealersService;
    }

    @RequestMapping("index.html")
    public String toIndex(){
        return "index";
    }

    @RequestMapping("question.html")
    public String toQuestion(Model model){
        List<QuestionBank> list = this.questionBankService.findAllQuestion();
        model.addAttribute("questionList",list);
        model.addAttribute("imgurl","http://47.94.255.160/");
        return "dati";
    }

    @RequestMapping("userinfo.html")
    public String toUser(Model model){
        List<String> citys=this.dealersService.getAllCity();
        Map<String,Object> map=new HashMap<>();
        List<Dealers> dealers=new ArrayList<>();
        for (String city:citys) {
            dealers=this.dealersService.getAllByCity(city);
            map.put(city, JSON.toJSON(dealers) );
        }
        List<Models> models=this.modelsService.findAllModel();
        model.addAttribute("map",map);
        model.addAttribute("models",models);
        return "liuzi";
    }

    @RequestMapping("share.html")
    public String toShare(){
        return "fenxiang";
    }

    @RequestMapping("img")
    public ResponseEntity<byte[]> draw( String time){
        String property = System.getProperty("user.dir");

        List<String> imagePaths= Arrays.asList("E:\\Idea\\TEST\\LapLap\\start-ser\\src\\main\\resources\\static\\images\\saft.jpg","E:\\Idea\\TEST\\LapLap\\start-ser\\src\\main\\resources\\static\\images\\healthy.jpg","E:\\Idea\\TEST\\LapLap\\start-ser\\src\\main\\resources\\static\\images\\huanbao.jpg");
        Random random=new Random();
        int rand=random.nextInt(2);
        //读取图片文件，得到BufferedImage对象
        BufferedImage bimg;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            bimg = ImageIO.read(new FileInputStream(imagePaths.get(rand)));

            //得到Graphics2D 对象
            Graphics2D g2d = (Graphics2D) bimg.getGraphics();
            //设置颜色和画笔粗细
            g2d.setColor(Color.white);
            g2d.setStroke(new BasicStroke(3));
            g2d.setFont(new Font("TimesRoman", Font.BOLD, 23));
            //绘制图案或文字
            g2d.drawString("完美！只用"+time+"秒就完成了所有答题！", 215, 510);
            //保存新图片
            ImageIO.write(bimg, "JPG", out);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentType(MediaType.IMAGE_JPEG);
           return  new ResponseEntity<>(out.toByteArray(), headers, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}