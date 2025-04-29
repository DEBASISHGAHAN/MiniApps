package com.project.miniapps.DigitalClock;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DigitalClock {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Digital Clock");
        JLabel timeLabel = new JLabel();

        frame.add(timeLabel);
        frame.setSize(200, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        Timer timer = new Timer(1000, e -> {
           String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
           timeLabel.setText(time);
        });
        timer.start();
    }
}
