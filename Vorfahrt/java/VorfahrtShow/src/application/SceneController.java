package application;


import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pLFileCreater.Auto;


public class SceneController {
	
	
	private Stage stage;
	private Scene scene;
	
	
	
	public void switchToEingabe(ActionEvent event) throws IOException {
		 Parent root = FXMLLoader.load(getClass().getResource("eingabe.fxml"));
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	

	public void switchToKreuzung(ActionEvent event, ArrayList<Auto> autos, int anzahlStrassen, ArrayList<String> hauptStrasse ) throws IOException {
		System.out.println("du Huso");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("kreuzung.fxml"));
		 Parent root = loader.load();
		kreuzungController controller = loader.getController();
			System.out.println("sc"+ autos.toString());
			
		controller.setAutos(autos);
		controller.setAnzahlStrassen(anzahlStrassen);
		controller.setHauptStrasse(hauptStrasse);
		
		
		stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		 
		    Platform.runLater(() -> {
            	System.out.print("hii2");
                // Call your function here
            	
                controller.init();
                
            });
		stage.show();
		}
}
