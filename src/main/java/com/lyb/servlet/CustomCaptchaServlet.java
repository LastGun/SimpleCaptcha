package com.lyb.servlet;

import com.lyb.bean.Producer;
import com.lyb.producer.CustomTextProducer;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.FlatColorBackgroundProducer;
import nl.captcha.gimpy.RippleGimpyRenderer;
import nl.captcha.noise.CurvedLineNoiseProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.NumbersAnswerProducer;
import nl.captcha.text.renderer.DefaultWordRenderer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


//自定义图片验证码servlet
public class CustomCaptchaServlet extends HttpServlet {

    private static int _width = 300;
    private static int _height = 50;
    //验证码字符数
    private final static int LENGTH=4;

    static CustomTextProducer text;
    /**
     * 颜色库
     */
    private static final java.util.List<Color> COLORS = new ArrayList(2);

    /**
     * 字体库
     */
    private static final java.util.List<Font> FONTS = new ArrayList(3);

    static {
        COLORS.add(new Color(24, 78, 190));

        FONTS.add(new Font("Geneva", 2, 48));
    }

    public CustomCaptchaServlet() {
    }

    //根据web.xml配置文件初始化参数，如验证码长与宽

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        if (this.getInitParameter("captcha-height") != null) {
            _height = Integer.valueOf(this.getInitParameter("captcha-height")).intValue();
        }

        if (this.getInitParameter("captcha-width") != null) {
            _width = Integer.valueOf(this.getInitParameter("captcha-width")).intValue();
        }

    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        try {
            DefaultWordRenderer wordRenderer = new DefaultWordRenderer(COLORS, FONTS);
            CustomTextProducer n=new CustomTextProducer();
            //定义字符集
            final char[] _srcChars=new char[]{'a','b','c','d','e','f','g','h','i','j',
                    'k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
                    '1', '2', '3', '4', '5', '6', '7', '8','9'};//
//           final char[] _srcChars=new char[]{'a','b','c','d'};
            String capText = "";//生成的验证码文本
            Random rand = new SecureRandom();
            for (int i = 0; i < LENGTH; ++i) {
                capText = capText + _srcChars[rand.nextInt(_srcChars.length)];
            }
            CustomTextProducer.setText(capText);
            ServletContext servletContext = getServletContext();
            //通过servlet上下文共享CustomTextProducer验证码对象
            servletContext.setAttribute("text",n.getText());
            //定义验证码
            Captcha captcha = (new Captcha.Builder(_width, _height)).addText( n, wordRenderer)
                    .addBackground(new FlatColorBackgroundProducer(new Color(255, 255, 255)))
                    .addNoise(new CurvedLineNoiseProducer(COLORS.get(0), 3.0F)).gimp
                            (new  RippleGimpyRenderer())
                    .build();
            //生成图片
            CaptchaServletUtil.writeImage(resp, captcha.getImage());
            //写入 Session
            req.getSession().setAttribute("simpleCaptcha", captcha);
        } catch (Exception e) {
                e.printStackTrace();
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
