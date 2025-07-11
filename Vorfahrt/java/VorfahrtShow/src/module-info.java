
module VorfahrtShow {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.fxml;
	requires javafx.base;
	requires javafx.media;
	requires java.desktop;
	requires javafx.swing;
	
	
	
	opens application to javafx.graphics, javafx.fxml;
}