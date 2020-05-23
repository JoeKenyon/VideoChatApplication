package com.company;

import java.util.Arrays;

public class RTPAudioPacket
{
    byte   packetType;
    int    sequenceNumber;
    int    payloadSize;
    byte[] payload;

    /**
     * Describe function here
     */
    public RTPAudioPacket(byte[] data)
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
    public RTPAudioPacket(int sequenceNumber, int payloadSize ,byte[] payload)
    {
        this.packetType     = 0;
        this.sequenceNumber = sequenceNumber;
        this.payloadSize    = payloadSize;
        this.payload        = payload;
    }

    /**
     * Describe function here
     */
    public RTPAudioPacket(int sequenceNumber, byte[] payload)
    {
        this.packetType     = 0;
        this.sequenceNumber = sequenceNumber;
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

    /**
     * Describe function here
     */
    @Override
    public String toString()
    {
        return "RTP AUDIO PACKET" + "\n" +
               "sequenceNumber: " + this.sequenceNumber + "\n" +
               "payloadSize   :"  + this.payloadSize    + "\n" +
               "payload       :"  + Arrays.toString(this.payload);
    }
}
