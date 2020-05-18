package com.company;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.DataLine.Info;

public class AudioRecorder
{
    private TargetDataLine targetDataLine;
    private AudioInputStream linearStream;
    private AudioFormat linearFormat = new AudioFormat(8000.0f, 16, 1, true, true);

    public AudioRecorder() throws LineUnavailableException {
        Info info = new Info(TargetDataLine.class, this.linearFormat);
        this.targetDataLine = (TargetDataLine)AudioSystem.getLine(info);
        this.targetDataLine.open(this.linearFormat);
        this.targetDataLine.start();
        this.linearStream = new AudioInputStream(this.targetDataLine);
    }

    public void close() {
        this.targetDataLine.stop();
        this.targetDataLine.close();
    }

    public byte[] getBlock() throws IOException {
        byte[] voiceData = new byte[512];
        this.linearStream.read(voiceData, 0, voiceData.length);
        return voiceData;
    }
}

