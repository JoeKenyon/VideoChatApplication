package com.company;

import java.util.Arrays;

public class RTPAudioPacket
{
    int sequenceNumber;
    int chunkX;
    int chunkY;
    int payloadSize;
    byte[] payload;

    public RTPAudioPacket(byte[] data)
    {

        //offset
        // number of bytes read
        int off = 0;

        //extract sequence number
        this.sequenceNumber = ((data[off++] & 0xFF) << 0) |
                ((data[off++] & 0xFF) << 8) |
                ((data[off++] & 0xFF) << 16 ) |
                ((data[off++] & 0xFF) << 24 );

        // extract payload size
        this.chunkX = ((data[off++] & 0xFF) << 0) |
                ((data[off++] & 0xFF) << 8) |
                ((data[off++] & 0xFF) << 16 ) |
                ((data[off++] & 0xFF) << 24 );

        // extract payload size
        this.chunkY = ((data[off++] & 0xFF) << 0) |
                ((data[off++] & 0xFF) << 8) |
                ((data[off++] & 0xFF) << 16 ) |
                ((data[off++] & 0xFF) << 24 );

        // extract payload size
        this.payloadSize = ((data[off++] & 0xFF) << 0) |
                ((data[off++] & 0xFF) << 8) |
                ((data[off++] & 0xFF) << 16 ) |
                ((data[off++] & 0xFF) << 24 );

        // extract payload
        this.payload = new byte[this.payloadSize];
        System.arraycopy(data,off, this.payload, 0, this.payloadSize);
    }

    public RTPAudioPacket(int sequenceNumber, int payloadSize ,byte[] payload)
    {
        this.sequenceNumber = sequenceNumber;
        this.payloadSize    = payloadSize;
        this.payload        = payload;
    }

    public byte[] toBytes()
    {
        assert payload != null;

        int headerSize = Integer.BYTES*2;
        byte[] packetData = new byte[this.payloadSize + headerSize];

        for(int i = 0; i < 4; i++) {
            packetData[i] = (byte)(this.sequenceNumber >>> (i * 8));
        }

        for(int i = 0; i < 4; i++) {
            packetData[i+4] = (byte)(this.payloadSize >>> (i * 8));
        }

        if (this.payloadSize >= 0)
            System.arraycopy(this.payload, 0, packetData, 8, this.payloadSize);

        return packetData;
    }

    @Override
    public String toString()
    {
        return "RTP AUDIO PACKET" + "\n" +
               "sequenceNumber: " + this.sequenceNumber + "\n" +
               "payloadSize   :"  + this.payloadSize    + "\n" +
               "payload       :"  + Arrays.toString(this.payload);
    }
}
