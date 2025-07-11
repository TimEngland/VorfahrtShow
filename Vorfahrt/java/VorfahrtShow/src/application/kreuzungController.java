package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import callPL.PlCaller;

import java.util.HashSet;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pLFileCreater.AnzahlStrassenException;
import pLFileCreater.Auto;
import pLFileCreater.PlCreater;

public class kreuzungController implements Initializable {

	@FXML 
	Canvas canvas;
	
	private ArrayList<Auto> autos = null;
	private int anzahlStrassen = 5;
	private ArrayList<String> hauptStrasse;
	
	
	
	
	public ArrayList<Auto> getAutos() {
		return autos;
	}



	public void setAutos(ArrayList<Auto> autos) {
		this.autos = autos;
	}



	public int getAnzahlStrassen() {
		return anzahlStrassen;
	}



	public void setAnzahlStrassen(int anzahlStrassen) {
		this.anzahlStrassen = anzahlStrassen;
	}



	public ArrayList<String> getHauptStrasse() {
		return hauptStrasse;
	}



	public void setHauptStrasse(ArrayList<String> hauptStrasse) {
		this.hauptStrasse = hauptStrasse;
	}


	private int middleX = 100;
	private int middleY = 100;
	
	// Einheitsvektoren der Straßen von 0,0
	private  ArrayList<ArrayList<Double>> strassenPunkte = new ArrayList<ArrayList<Double>>();
	//1,2 Straße 1; 3,4 Straße 2;....
	private  ArrayList<ArrayList<Double>> inPoints = new ArrayList<ArrayList<Double>>();

	
	
	private void draw(GraphicsContext gc) {
		 gc.setFill(Color.GREEN);
	        gc.setStroke(Color.BLACK);
	        gc.setLineWidth(2);
	        
	        gc.strokeLine(40, 10, 10, 40);
	}
	
	
	 
	private void drawKreuzung(GraphicsContext gc, int strassenAnzahl) {
		 gc.setStroke(Color.BLACK);
	     gc.setLineWidth(5);
		
	    
		double winkel = -(2*Math.PI / strassenAnzahl);
		float strassenBreite = 15;
		float strassenLaenge = 50;
		float kreuzungRadius = 30;
		
		
		
		 for(int i = 0; i< strassenAnzahl; i++) {
			ArrayList<Double> strassenPunkt = new ArrayList<Double>();
			double x = Math.cos(i*winkel);
			double y = Math.sin(i*winkel);
			strassenPunkt.add(x);
			strassenPunkt.add(y);
			strassenPunkte.add(strassenPunkt);
		}
		
		 
		
		
		
		
		for(ArrayList<Double> strassenPunkt: strassenPunkte) {
			ArrayList<Double> rechterWinkel = new ArrayList<Double>();
			rechterWinkel.add(strassenPunkt.get(1));
			rechterWinkel.add(-strassenPunkt.get(0));
			double x11 = middleX + strassenPunkt.get(0) * kreuzungRadius + rechterWinkel.get(0) * strassenBreite;
			double y11 = middleY + strassenPunkt.get(1) * kreuzungRadius + rechterWinkel.get(1) * strassenBreite;
			double x12 = x11 + strassenLaenge * strassenPunkt.get(0);
			double y12 = y11 + strassenLaenge * strassenPunkt.get(1);
			
			double x21 = middleX + strassenPunkt.get(0) * kreuzungRadius - rechterWinkel.get(0) * strassenBreite;
			double y21 = middleY + strassenPunkt.get(1) * kreuzungRadius - rechterWinkel.get(1) * strassenBreite;
			double x22 = x21 + strassenLaenge * strassenPunkt.get(0);
			double y22 = y21 + strassenLaenge * strassenPunkt.get(1);
			
			gc.strokeLine(x11, y11, x12, y12);
			gc.strokeLine(x21, y21, x22, y22);
			
			inPoints.add(new ArrayList<Double>(List.of(x21, y21)));
			inPoints.add(new ArrayList<Double>(List.of(x11, y11)));
	
			
		}
		
		
		for(int i = 0; i < inPoints.size()/2  ; i++) {
			gc.strokeLine(inPoints.get(i*2+1).get(0), inPoints.get(i*2+1).get(1), 
					inPoints.get((i*2+2)% inPoints.size() ).get(0), inPoints.get((i*2+2) % inPoints.size()).get(1) );
		}
	}
	
	
	private void drawCarPath(String Zufahrt, String Ausfahrt, GraphicsContext gc) {
		String alphabet10 = "abcdefghij";
		int zufahrt = alphabet10.indexOf(Zufahrt);
		int ausfahrt = alphabet10.indexOf(Ausfahrt);
		
		ArrayList<Double> p1 = inPoints.get(2*zufahrt);
		ArrayList<Double> p2 = inPoints.get(2*zufahrt+1);
		
		
		
		ArrayList<Double> zufahrtPunkt = new ArrayList<Double>();
		zufahrtPunkt.add(  p2.get(0) + ((p1.get(0)-p2.get(0))/3)  );
		zufahrtPunkt.add(  p2.get(1) + ((p1.get(1)-p2.get(1))/3)  );
		
		gc.setStroke(Color.GREEN);
		
		gc.strokeLine(	zufahrtPunkt.get(0) - 10* strassenPunkte.get(zufahrt).get(0),
						zufahrtPunkt.get(1) - 10* strassenPunkte.get(zufahrt).get(1),
						zufahrtPunkt.get(0) + 30* strassenPunkte.get(zufahrt).get(0),
						zufahrtPunkt.get(1) + 30* strassenPunkte.get(zufahrt).get(1)
				);
				
				
				
		
		
		
		ArrayList<Double> p3 = inPoints.get(2*ausfahrt);
		ArrayList<Double> p4 = inPoints.get(2*ausfahrt+1);
		
		ArrayList<Double> ausfahrtPunkt = new ArrayList<Double>();
		ausfahrtPunkt.add(  p3.get(0) + ((p4.get(0)-p3.get(0))/3)  );
		ausfahrtPunkt.add(  p3.get(1) + ((p4.get(1)-p3.get(1))/3)  );
		
		
		gc.setStroke(Color.RED);
		
		gc.strokeLine(	ausfahrtPunkt.get(0) - 10* strassenPunkte.get(ausfahrt).get(0),
						ausfahrtPunkt.get(1) - 10* strassenPunkte.get(ausfahrt).get(1),
						ausfahrtPunkt.get(0) + 30* strassenPunkte.get(ausfahrt).get(0),
						ausfahrtPunkt.get(1) + 30* strassenPunkte.get(ausfahrt).get(1)
		);
		
		gc.setStroke(Color.YELLOW);
		
		gc.strokeLine(	ausfahrtPunkt.get(0) - 10* strassenPunkte.get(ausfahrt).get(0),
						ausfahrtPunkt.get(1) - 10* strassenPunkte.get(ausfahrt).get(1),
						zufahrtPunkt.get(0) - 10* strassenPunkte.get(zufahrt).get(0),
						zufahrtPunkt.get(1) - 10* strassenPunkte.get(zufahrt).get(1)
					);	 
		
		gc.setStroke(Color.BLACK);
	}
	
	
	public void drawRound(GraphicsContext gc) {
		HashSet<String> akk = new HashSet<String>();
		ArrayList<Auto> round = new ArrayList<Auto>();
		ArrayList<Auto> leftBehind = new ArrayList<Auto>();
		for(Auto a : autos) {
			if(akk.add(a.getZufahrt())){
				round.add(a);
			}
			else {leftBehind.add(a);}
		}
		try {
			PlCreater.createPLFile(
					PlCreater.createProlog(anzahlStrassen, round, hauptStrasse),
					"C:\\Users\\karat\\Documents\\Prolog\\java.pl");
		} catch (AnzahlStrassenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("hier1 "+ leftBehind.toString());
		
		ArrayList<String> halt= PlCaller.callPL();
		System.out.println("hier2 "+ halt.toString());
		
		ArrayList<Auto> leftStanding = new ArrayList<Auto>();
		
		for(int i = 0; i < halt.size(); i++) {
			System.out.println(round.get(i).getZufahrt());
			System.out.println(halt.get(i));
			if(halt.get(i).equals("false")) {
				System.out.println(round.get(i).getZufahrt());
				drawCarPath(round.get(i).getZufahrt(), round.get(i).getAbfahrt(), gc);
			}
			else {leftStanding.add(round.get(i));}
		}
		leftStanding.addAll(leftBehind);
		this.setAutos(leftStanding);
		
	}
	@FXML
	public void init(ActionEvent event) {
		
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, canvas.getHeight(), canvas.getWidth());
		drawKreuzung(gc, anzahlStrassen);
		drawRound(gc);
		
	}
	
	public void init() {
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawKreuzung(gc, anzahlStrassen);
		//drawRound(gc);
	
	}
	
	
	@FXML
	public void swichToEingabe(ActionEvent event) {
		SceneController sc = new SceneController();
		try {
			sc.switchToEingabe(event);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
