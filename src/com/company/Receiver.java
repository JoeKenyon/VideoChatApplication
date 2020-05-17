package com.company;

public class Receiver implements Runnable
{
    void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {

    }
}
