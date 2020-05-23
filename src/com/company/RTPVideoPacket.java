package com.company;

import java.util.Arrays;

public class RTPVideoPacket
{
    byte   packetType;
    int    sequenceNumber;
    int    chunkX;
    int    chunkY;
    int    payloadSize;
    byte[] payload;

    /**
     * Describe function here
     */
    public RTPVideoPacket(byte[] data)
    {
        //offset
        // number of bytes read
        int off = 0;

        this.packetType    = data[off++];

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

    /**
     * Describe function here
     */
    public RTPVideoPacket(int sequenceNumber, int chunkX, int chunkY, int payloadSize ,byte[] payload)
    {
        this.packetType     = 1;
        this.sequenceNumber = sequenceNumber;
        this.chunkX         = chunkX;
        this.chunkY         = chunkY;
        this.payloadSize    = payloadSize;
        this.payload        = payload;
    }

    /**
     * Describe function here
     */
    public RTPVideoPacket(int sequenceNumber, int chunkX, int chunkY, byte[] payload)
    {
        this.packetType     = 1;
        this.sequenceNumber = sequenceNumber;
        this.chunkX         = chunkX;
        this.chunkY         = chunkY;
        this.payloadSize    = payload.length;
        this.payload        = payload;
    }

    /**
     * Describe function here
     */
    public byte[] toBytes()
    {
        assert payload != null;

        int packetSize =
                Byte.BYTES     +  // packet type
                Integer.BYTES  +  // sequence number
                Integer.BYTES  +  // chunkX
                Integer.BYTES  +  // chunkY
                Integer.BYTES  +  // payload size
                this.payloadSize; // payload


        byte[] packetData = new byte[packetSize];

        int off = 0;

        // packet type
        packetData[off++] = this.packetType;

        // sequence number
        for(int i = 0; i < 4; i++) {
            packetData[off++] = (byte)(this.sequenceNumber >>> (i * 8));
        }

        // chunkX
        for(int i = 0; i < 4; i++) {
            packetData[off++] = (byte)(this.chunkX >>> (i * 8));
        }

        // chunkY
        for(int i = 0; i < 4; i++) {
            packetData[off++] = (byte)(this.chunkY >>> (i * 8));
        }

        // payload size
        for(int i = 0; i < 4; i++) {
            packetData[off++] = (byte)(this.payloadSize >>> (i * 8));
        }

        // payload
        for(int i = 0; i < this.payloadSize; i++){
            packetData[off++] = this.payload[i];
        }

        return packetData;
    }

    @Override
    public String toString()
    {
        return "RTP VIDEO PACKET" + "\n" +
                "sequenceNumber: " + this.sequenceNumber + "\n" +
                "chunkX        :"  + this.chunkX         + "\n" +
                "chunkY        :"  + this.chunkY         + "\n" +
                "payloadSize   :"  + this.payloadSize    + "\n" +
                "payload       :"  + Arrays.toString(this.payload);
    }
}

