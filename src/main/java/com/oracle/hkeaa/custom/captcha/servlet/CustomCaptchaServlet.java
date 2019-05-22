package com.oracle.hkeaa.custom.captcha.servlet;

import com.oracle.hkeaa.custom.captcha.producer.CustomProducer;
import nl.captcha.Captcha;
import nl.captcha.backgrounds.GradiatedBackgroundProducer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.renderer.ColoredEdgesWordRenderer;
import nl.captcha.text.renderer.WordRenderer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;


//自定义图片验证码servlet
public class CustomCaptchaServlet extends HttpServlet {

    private static int _width = 150;
    private static int _height = 30;
    //验证码字符数
    private final static int LENGTH=5;

    /**
     * 颜色库
     */
    private static final java.util.List<Color> COLORS = new ArrayList(2);

    /**
     * 字体库
     */
    private static final java.util.List<Font> FONTS = new ArrayList(3);

    static {
        COLORS.add(Color.BLACK);
        COLORS.add(Color.BLUE);

        FONTS.add(new Font("Geneva", Font.ITALIC, 48));
        FONTS.add(new Font("Courier", Font.BOLD, 48));
        FONTS.add(new Font("Arial", Font.BOLD, 48));
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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        this.doGet(req, resp);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp)  {

        HttpSession session = req.getSession();
        Captcha captcha;
        if (session.getAttribute(Captcha.NAME) == null) {
            captcha = buildAndSetCaptcha(session);
        } else {
            captcha = (Captcha)session.getAttribute(Captcha.NAME);
        }
        System.out.println("image session.getId():"+session.getId()+"  customText:"+captcha.getAnswer());

        CaptchaServletUtil.writeImage(resp, captcha.getImage());
    }

    private Captcha buildAndSetCaptcha(HttpSession session) {

        //定义字符集
        final char[] _srcChars=new char[]{'A','B','C','D','E','F','G','H','I','J',
                'K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y',
                '1', '2', '3', '4', '5', '6', '7', '8','9'};//

        String capText = "";//生成的验证码文本
        Random rand = new SecureRandom();
        for (int i = 0; i < LENGTH; ++i) {
            capText = capText + _srcChars[rand.nextInt(_srcChars.length)];
        }
        CustomProducer cp=new CustomProducer();
        cp.setText(capText);

        Captcha captcha =
                 new Captcha.Builder(_width, _height).addText(cp).gimp().addNoise().addBackground(new GradiatedBackgroundProducer()).build();

        session.setAttribute(Captcha.NAME, captcha);
        return captcha;
    }


}
