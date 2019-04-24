package com.lyb.producer;

import nl.captcha.audio.Sample;
import nl.captcha.audio.producer.VoiceProducer;
import nl.captcha.util.FileUtil;

import java.util.Map;

public class CustomVoiceProducer implements VoiceProducer {

    private final Map<Integer, String> _voices;

    public CustomVoiceProducer(Map<Integer, String> voices) {
        this._voices = voices;
    }
    public final Sample getVocalization(char num) {
        //查找每个发音对应的语音资源文件
        String filename="/wav/"+num+".wav";
        return FileUtil.readSample(filename);
    }
}
