package com.lyb.producer;

import nl.captcha.text.producer.TextProducer;

public class CustomProducer implements TextProducer {

    private String text;

    public void setText(String text) {
        this.text=text;
    }

    public String getText() {
        return this.text;
    }
}
