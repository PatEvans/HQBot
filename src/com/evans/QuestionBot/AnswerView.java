package com.evans.QuestionBot;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class AnswerView extends JPanel{
  AnswerView(BufferedImage ansImage){
	  repaint();
  }
  public void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(QuestionView.getAnswers(), 0, 0,500, 300, null);
     
      
  }
}
