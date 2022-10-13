/* Javadocs folder is located in C:\Users\mycha\Desktop\SoftWare1\inventoryapp\Javadocs
* inside a folder labeled Javadocs at the same level as src*/


package gray.inventoryapp.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import gray.inventoryapp.model.*;

import java.io.IOException;

/**
 *Initialize application and populate Parts and Products Tables
 * Future Enhancements:OutSourced parts should be able to be associated with multiple company names, as
 * it is very likely that multiple companies may offer the same part.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/gray/inventoryapp.view/MainScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 400);
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

        Part seat = new InHouse(2633, "Large Seat", 55.00, 30, 5, 50, 67);
        Inventory.addPart(seat);
        Part wheel = new InHouse(3785, "Large Wheel", 100.00, 20, 5, 50, 69);
        Inventory.addPart(wheel);
        Part handleBars = new OutSourced(1246, "High handle Bars", 60.00, 70, 5, 90, "Harolds Handle Bars");
        Inventory.addPart(handleBars);
        Part brakePad = new OutSourced(2673, "Brake Pad", 10.00, 50, 5, 90, "Bills Brake Pads");
        Inventory.addPart(brakePad);
        Product konaSutra = new Product(4758,"Kona Sutra", 1200.00, 6, 1, 10);
        Inventory.addProduct(konaSutra);
        Product eBike = new Product(4998,"Model R", 2000.00, 5, 1, 10);
        Inventory.addProduct(eBike);

        launch();
    }
}