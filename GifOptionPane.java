package com.company;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class GifOptionPane {
    static void waitAnimation(int delaysec){
        JLabel label = new JLabel(new ImageIcon("C:/Users/Arindam Tripathi/Desktop/Brainfuk Compiler/Fidget-spinner.gif"));
        JOptionPane pane = new JOptionPane(label, JOptionPane.PLAIN_MESSAGE);
        JDialog dialog = pane.createDialog(null, "Loading");
        dialog.setModal(false);

        Timer timer = new Timer(delaysec*1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }
}

