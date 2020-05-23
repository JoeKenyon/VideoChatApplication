package com.company;

import java.util.Arrays;

public class RTPVideoPacket
{
    int sequenceNumber;
    int payloadSize;
    byte[] payload;

    public RTPAudioPacket(byte[] data)
    {
        //extract sequence number
        this.sequenceNumber = ((data[4] & 0xFF) << 24) |
                ((data[2] & 0xFF) << 16) |
                ((data[1] & 0xFF) << 8 ) |
                ((data[0] & 0xFF) << 0 );

        // extract payload size
        this.payloadSize = ((data[7] & 0xFF) << 24) |
                ((data[6] & 0xFF) << 16) |
                ((data[5] & 0xFF) << 8 ) |
                ((data[4] & 0xFF) << 0 );

        // extract payload
        this.payload = new byte[this.payloadSize];
        System.arraycopy(data,8, this.payload, 0, this.payloadSize);
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

        for(int i = 0; i < this.payloadSize; i++){
            packetData[i+8] = this.payload[i];
        }

        return packetData;
    }

    @Override
    public String toString()
    {
        return "RTP PACKET" + "\n" +
                "sequenceNumber: " + this.sequenceNumber + "\n" +
                "payloadSize   :"  + this.payloadSize    + "\n" +
                "payload       :"  + Arrays.toString(this.payload);
    }
}

