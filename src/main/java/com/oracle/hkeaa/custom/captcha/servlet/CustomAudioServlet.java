package com.oracle.hkeaa.custom.captcha.servlet;

import com.oracle.hkeaa.custom.captcha.producer.CustomProducer;
import com.oracle.hkeaa.custom.captcha.producer.CustomVoiceProducer;
import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.servlet.CaptchaServletUtil;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

public class CustomAudioServlet extends HttpServlet {
    //AudioCaptchaServlet
    public CustomAudioServlet() {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {

        Map<Integer, String> voicesMap = new HashMap<Integer, String>();

        HttpSession session=req.getSession();
        Captcha captcha = (Captcha)session.getAttribute(Captcha.NAME);
        String text=captcha.getAnswer();
        System.out.println("audio session.getId():"+session.getId()+"  customText:"+text);

        if(text==null){
            text="abcde";
        }
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
        CaptchaServletUtil.writeAudio(resp, ac.getChallenge());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        this.doGet(req, resp);
    }
}
