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
}
