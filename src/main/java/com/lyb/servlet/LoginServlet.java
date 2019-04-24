package com.lyb.servlet;

import nl.captcha.Captcha;
import nl.captcha.audio.AudioCaptcha;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        System.out.println("init....");
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("getting....");
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        super.doPost(req, resp);
        String answer = req.getParameter("answer");
        Captcha captcha = (Captcha)req.getSession().getAttribute(Captcha.NAME);
        AudioCaptcha audioCaptcha = (AudioCaptcha)req.getSession().getAttribute(AudioCaptcha.NAME);

        req.setCharacterEncoding("UTF-8"); // Do this so we can capture non-Latin chars

        if (captcha.isCorrect(answer)){
            req.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(req,resp);
        }else{

            req.getRequestDispatcher("WEB-INF/jsp/error.jsp").forward(req,resp);

        }

    }
}
