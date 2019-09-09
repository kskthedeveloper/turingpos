package com.turing.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test {
    public JPanel myPanel;
    private JButton myButton;
    private JTable table1;

    public Test() {
        myButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Testing");
            }
        });
    }
}
