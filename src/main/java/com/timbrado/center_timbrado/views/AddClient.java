package com.timbrado.center_timbrado.views;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddClient extends Application {
	
	public static void main( String args[] ) {
		System.out.println("Prueba");
		launch( args );
		System.out.println("Lanzar");
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try{
			Parent parent = FXMLLoader.load(this.getClass().getResource("/fxml/addClient.fxml"));
			Scene scene = new Scene( parent );
			primaryStage.setScene( scene );
			primaryStage.setTitle("Añadir Cliente");
			primaryStage.show();
		}
		catch (IOException e) {
			
		}
		
	}
}