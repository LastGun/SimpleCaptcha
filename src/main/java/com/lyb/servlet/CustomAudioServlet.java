package com.lyb.servlet;

import com.lyb.producer.CustomProducer;
import com.lyb.producer.CustomVoiceProducer;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.servlet.CaptchaServletUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAudioServlet extends HttpServlet {
    //AudioCaptchaServlet
    public CustomAudioServlet() {
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        Map<Integer, String> voicesMap = new HashMap<Integer, String>();

        ServletContext servletContext = getServletContext();
        //从servlet上下文获取已生成的文本验证码
        String text = (String) servletContext.getAttribute("text");
//        System.out.println("audio text:"+text);
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            voicesMap.put(i,String.valueOf(chars[i]));
        }

        VoiceProducer vProd = new CustomVoiceProducer(voicesMap);
        CustomProducer textProducer=new CustomProducer();
        textProducer.setText(text);
        AudioCaptcha ac = new AudioCaptcha.Builder()
             .addAnswer(textProducer)//语音对应的文本对象
             .addVoice(vProd)//添加语音
             .addNoise()//添加噪音
             .build();
        req.getSession().setAttribute("audioCaptcha", ac);
        CaptchaServletUtil.writeAudio(resp, ac.getChallenge());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
