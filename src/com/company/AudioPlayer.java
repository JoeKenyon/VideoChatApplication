package com.company;

import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine.Info;

public class AudioPlayer {
    private AudioFormat linearFormat = new AudioFormat(8000.0F, 16, 1, true, false);
    private SourceDataLine sourceDataLine;

    public AudioPlayer() throws LineUnavailableException
    {
        Info info2 = new Info(SourceDataLine.class, this.linearFormat);
        this.sourceDataLine = (SourceDataLine)AudioSystem.getLine(info2);
        this.sourceDataLine.open(this.linearFormat);
        this.sourceDataLine.start();
    }

    public void playBlock(byte[] voiceData) throws IOException
    {
        this.sourceDataLine.write(voiceData, 0, voiceData.length);
    }

    public void close() {
        this.sourceDataLine.drain();
        this.sourceDataLine.stop();
        this.sourceDataLine.close();
    }
}

