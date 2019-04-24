package com.lyb.producer;

import nl.captcha.text.producer.TextProducer;

import java.security.SecureRandom;
import java.util.Random;

public class CustomTextProducer implements TextProducer {
    private static final Random rand = new SecureRandom();
//某些字符相似容易导致用户误输，比如 i 与 1、z 与 2，所以我们自己定制了字符生成器：

    private static String text;
    /**
     * 默认长度
     */
    private static final int DEFAULT_LENGTH = 4;
    /**
     * 字符集
     */
//    private static final char[] DEFAULT_CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'k', 'm', 'n', 'p', 'r', 'w', 'x', 'y', '2', '3', '4', '5', '6', '7', '8'};
    private static final char[] DEFAULT_CHARS = new char[]{'a', '2', '3', '4', '5', '6', '7', '8'};
    private final int _length;
    private final char[] _srcChars;

    public CustomTextProducer() {
        this(DEFAULT_LENGTH, DEFAULT_CHARS);
    }

    public CustomTextProducer(int length, char[] srcChars) {
        this._length = length;
        this._srcChars = copyOf(srcChars, srcChars.length);
    }


    public String getText() {
        return CustomTextProducer.text;
//        String capText = "";
//
//        for (int i = 0; i < this._length; ++i) {
//            capText = capText + this._srcChars[RAND.nextInt(this._srcChars.length)];
//        }
//
//        return capText;

    }

    public static char[] copyOf(char[] original, int newLength) {
        char[] copy = new char[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public static void setText(String text) {
        CustomTextProducer.text = text;
    }
}
