package com.evans.QuestionBot;
import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.google.api.Page;
import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.Block;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.cloud.vision.v1.Paragraph;
import com.google.cloud.vision.v1.Symbol;
import com.google.cloud.vision.v1.TextAnnotation;
import com.google.cloud.vision.v1.Word;
import com.google.protobuf.ByteString;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class QuestionView extends JPanel{
	private static BufferedImage qImage;
	private static BufferedImage ansImage;
    private static JPanel answerView;
	
	QuestionView(){
	  
      captureScreen();
      setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
      add(Box.createRigidArea(new Dimension(0, 200)));
     
      add(new AnswerView(ansImage));
      
	}
	
	public void captureScreen() {
		try {
            Robot robot = new Robot();
            
            Rectangle screenRect = new Rectangle(500,1000);
            BufferedImage screenImage = robot.createScreenCapture(screenRect);
            String questionPath = "question.jpg";
            String answerPath = "answer.jpg";
            qImage = screenImage.getSubimage(60, 200, 500-2*30, 400-2*100);
            ansImage = screenImage.getSubimage(60, 405, 500-2*30, 1000-2*320);
            ImageIO.write(qImage, "jpg",new File(questionPath));
            ImageIO.write(ansImage, "jpg",new File(answerPath));
            
            String questionString=detectDocumentText(questionPath, new PrintStream(System.out)).replace("\n", " ");

            String answerString=detectDocumentText(answerPath, new PrintStream(System.out));
            String answers[] = answerString.split("\\r?\\n");
            
            System.out.println(questionString);
            System.out.println(answers[0]);
            System.out.println(answers[1]);
            System.out.println(answers[2]);
            Analysis.resultsAnalysis(questionString,answers);
            this.repaint();
            
        } catch (Exception ex) {
            System.err.println(ex);
        }
	}
	
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(qImage, 0, 0,500, 200, null);
       
        
    }
	
	public static BufferedImage getAnswers() {
		return ansImage;
	}
	
	public static String detectDocumentText(String filePath, PrintStream out) throws Exception,
    IOException {
		List<AnnotateImageRequest> requests = new ArrayList<>();
        String fullString="";
		ByteString imgBytes = ByteString.readFrom(new FileInputStream(filePath));

		Image img = Image.newBuilder().setContent(imgBytes).build();
		Feature feat = Feature.newBuilder().setType(Type.DOCUMENT_TEXT_DETECTION).build();
		AnnotateImageRequest request =
				AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
		requests.add(request);

		try (ImageAnnotatorClient client = ImageAnnotatorClient.create()) {
			BatchAnnotateImagesResponse response = client.batchAnnotateImages(requests);
			List<AnnotateImageResponse> responses = response.getResponsesList();
			client.close();

			for (AnnotateImageResponse res : responses) {
				if (res.hasError()) {
					out.printf("Error: %s\n", res.getError().getMessage());
					return null;
				}

				// For full list of available annotations, see http://g.co/cloud/vision/docs
				TextAnnotation annotation = res.getFullTextAnnotation();
				for (com.google.cloud.vision.v1.Page page: annotation.getPagesList()) {
					String pageText = "";
					for (Block block : page.getBlocksList()) {
						String blockText = "";
						for (Paragraph para : block.getParagraphsList()) {
							String paraText = "";
							for (Word word: para.getWordsList()) {
								String wordText = "";
								for (Symbol symbol: word.getSymbolsList()) {
									wordText = wordText + symbol.getText();
								}
								paraText = paraText + wordText;
							}
							
							blockText = blockText + paraText;
						}
						pageText = pageText + blockText;
					}
				}
				fullString+=(annotation.getText());
			}
			
      }
		
		return fullString;
  }
	  
}
