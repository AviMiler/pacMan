package services;

import data.ineerDB.Arrays;

public class Timer {

    int time;

    public static Arrays<Timer> timers = new Arrays<>();

    public static void ticAll(){
        for (int i = 0; i < timers.size(); i++) {
            timers.get(i).tic();
        }
    }

    public Timer(int seconds) {
        time = seconds;
    }

    public void tic(){
        if (time>0)
            time--;
    }

    public void reset(){
        time = -1;
    }

    public static void waitFor(int seconds){
        for (int i = 0; i < seconds*60; i++) {
            waitFor();
        }
    }

    public static void waitFor() {

        final int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
        double drawDelta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (true) {
            currentTime = System.nanoTime();
            drawDelta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (drawDelta >= 1) {
                return;
            }
        }
    }

}
