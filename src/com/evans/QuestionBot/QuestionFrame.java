package com.evans.QuestionBot;
import javax.swing.*;
public class QuestionFrame extends JFrame{
	
	private OptionsPane options = new OptionsPane();
	private QuestionView questions = new QuestionView();
	
    QuestionFrame(){
    	JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
    			options, questions);
    	
    	add(splitPane);
    	setDefaultCloseOperation(EXIT_ON_CLOSE);
    	
    	setVisible(true);
    	setSize(650,530);
    }	
    
    public void setOptions() {
    	options=new OptionsPane();
    	repaint();
    } 
    
    public void setQuestions() {
    	questions=new QuestionView();
    	repaint();
    }
}
