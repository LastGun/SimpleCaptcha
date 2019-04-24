package com.lyb.bean;

import nl.captcha.text.producer.TextProducer;

public class Producer {
    private static TextProducer text;

    public static TextProducer getText() {
        return text;
    }

    public static void setText(TextProducer text) {
        Producer.text = text;
    }
}
