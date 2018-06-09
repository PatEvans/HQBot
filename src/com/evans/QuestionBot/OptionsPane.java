package com.evans.QuestionBot;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class OptionsPane extends JPanel implements ActionListener{
	
    OptionsPane(){
    	setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    	
    	JButton captureButton=new JButton("Capture Screen");
	    captureButton.addActionListener(this);
	    add(captureButton);
    }
    
    public void actionPerformed(ActionEvent e) { 
        QuestionBot.frame.setQuestions();
    }
}
