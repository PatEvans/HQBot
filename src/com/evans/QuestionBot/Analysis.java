package com.evans.QuestionBot;

import java.net.URI;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Analysis {
   public static void resultsAnalysis(String queryString,String[] answers) {
	   try {
		   String uriString="https://www.google.com/search?q="+
			   		queryString.replace(" ", "+").replace("-", "").replace("\"", "%22")+"+"+
			   		answers[0].replace(" ", "+").replace("-", "").replace("\"", "%22")+"+"+
			   		answers[1].replace(" ", "+").replace("-", "").replace("\"", "%22")+"+"+
			   		answers[2].replace(" ", "+").replace("-", "").replace("\"", "%22")
			   		;
		   
		   while(uriString.substring( uriString.length() - 1).equals("+")) {
			   uriString=uriString.substring(0,  uriString.length() - 1);
		   }
		   
		   URI url = new URI( uriString);
		  
		   java.awt.Desktop.getDesktop().browse(url);
		   
		   //Unused method for search results counting
		   //Document document = Jsoup //
          //                	.connect(url) //
          //                	.userAgent("Mozilla/5.0 (Windows; U; MSIE 6.0; Windows NT 5.1; SV1; .NET CLR 2.0.50727)") //
          //                	.get();

		  // Element divResultStats = document.select("div#resultStats").first();
		  // if (divResultStats==null) {
			//   throw new RuntimeException("Unable to find results stats.");
		   //}

		  //return divResultStats.text();
		   
	   } catch (Exception ex) {
           System.err.println(ex);
       }
   }
   
   //unused word replace idea
   public String wordReplace(String questionString){
		questionString=questionString.replaceAll("Which\\sof\\sthese|which\\sof\\sthese"
												+ "|Which\\sof\\sthe\\sfollowing"
												+ "|which\\sof\\sthe\\sfollowing\""
												+ "|\\bWhich\\b|\\bWhat\\b", "what")
										.replaceAll("\\bCountries\\b|\\bcountries\\b", "country")
										.replaceAll("\\bPrincipally\\b|\\bprincipally\\b|"
												+ "actor|actors|performers|performer|"
												+ "musicians|Musicians","");
		System.out.println(questionString);
		return questionString;
	}
}
