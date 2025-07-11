package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import pLFileCreater.Auto;


public class eingabeController implements Initializable {

	
	@FXML
	public TextField a1Zufahrt;
	
	@FXML
	public Label streetCount;
	
	@FXML 
	public VBox autoInput;
	
	@FXML
	public TextField haupt1;
	
	@FXML 
	public TextField haupt2;
	
	
	
	private int anzahlStrassen = 3;
	
	private int anzahlAutos = 0;
	
	private ChangeListener<String> validator = new ChangeListener<String>() {
		public void changed(javafx.beans.value.ObservableValue<? extends String> obs, String oldValue, String newValue) {
			if(newValue != null) {
	            if(!validStreet(newValue)){
	            	TextField textField = (TextField) ((ReadOnlyProperty<String>) obs).getBean();
	            	if(textField.getText() != "") {
	            		 Platform.runLater(() -> {
	                         textField.setText("");
	                     });}
	            } 
			}
		};
	};
	
	public boolean validStreet(String input) {
		if(!(input.length() == 1)) {return false;}
		char[] alpha = "abcdefghij".toCharArray();
		char[] currAlpha = Arrays.copyOf(alpha, anzahlStrassen);
		for(char c : currAlpha) {
			if(c == input.charAt(0)) {return true;}
		}
		return false;
		
	}
	
	@FXML
	private void addStreet(ActionEvent event) {
		if(anzahlStrassen < 10) {anzahlStrassen++;}
		streetCount.setText(((Integer) anzahlStrassen).toString());
	}
	
	@FXML
	private void subtractStreet(ActionEvent event) {
		if(anzahlStrassen > 3) {anzahlStrassen--;}
		streetCount.setText(((Integer) anzahlStrassen).toString());
		
	}
	
	public HBox createAutoInput(int i) {
		 HBox hbox = new HBox();
	        hbox.setPrefHeight(33.0);
	        hbox.setPrefWidth(134.0);
	        hbox.setSpacing(30.0);
	        hbox.setId("a"+i+"Input");

	        // Create the Label
	        Label label = new Label("a"+i);

	        // Create the first TextField (equivalent to fx:id="a1Zufahrt")
	        TextField zufahrt = new TextField();
	        zufahrt.setMinWidth(30.0);
	        zufahrt.setPrefWidth(30.0);
	        zufahrt.setId("a"+i+"Zufahrt");
	    	zufahrt.textProperty().addListener(validator);

	        // Create the second TextField
	        TextField abfahrt = new TextField();
	        abfahrt.setMinWidth(30.0);
	        abfahrt.setPrefWidth(30.0);
	        abfahrt.setId("a"+i+"Abfahrt");
	    	abfahrt.textProperty().addListener(validator);
	        // Add all to the HBox
	        hbox.getChildren().addAll(label, zufahrt, abfahrt);
	        return hbox;
	}
	
	@FXML
	public void insertAutoInput(ActionEvent event) {
		int i = autoInput.getChildrenUnmodifiable().size();
		autoInput.getChildren().add(i-1, createAutoInput(anzahlAutos));
		anzahlAutos++;
	}
	
	@FXML
	public void deleteAutoInput(ActionEvent event) {
		int i = autoInput.getChildrenUnmodifiable().size();
		if(i > 2) {
		autoInput.getChildren().remove(i-2);
		anzahlAutos--;
		}
	}
	
	public ArrayList<Auto> readAutos(){
		ArrayList<Auto> autos = new ArrayList<Auto>();
		for(int i = 0; i < anzahlAutos; i++) {
			HBox hbox = (HBox) FXNodeFinder.findNodeById(autoInput, "a"+i+"Input");
			Auto auto = new Auto(((TextField) hbox.getChildren().get(1)).getText(),
					((TextField) hbox.getChildren().get(2)).getText()
					);
			if(auto.getZufahrt()!= null && auto.getAbfahrt()!= null) {
				autos.add(auto);
				System.out.println(auto.toString());
			}
		}
		System.out.print(autos);
		return autos;
	}
	
	public ArrayList<String> readHauptStrasse(){
		ArrayList<String> res = new ArrayList<String>();
		
		String h1 = haupt1.getText();
		String h2 = haupt2.getText();
		if(!h1.trim().isEmpty() && !h2.trim().isEmpty()) {
			res.add(h1);
			res.add(h2);
			return res;
		}
	return null;
	}
	
	@FXML
	public void simulate(ActionEvent event) {
		SceneController sc = new SceneController();
		try {
			sc.switchToKreuzung(event, readAutos(), anzahlStrassen,readHauptStrasse());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		haupt1.textProperty().addListener(validator);
		haupt2.textProperty().addListener(validator);
	
		
	}

}
