package gray.inventoryapp.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import gray.inventoryapp.model.*;

import java.io.IOException;

public class AddPartController {

    @FXML
    private Button addPartCancelButton;

    @FXML
    private RadioButton addPartFormInHouseRadio;

    @FXML
    private Button addPartFormSave;

    @FXML
    private TextField addPartName;

    @FXML
    private TextField addPartInv;

    @FXML
    private TextField addPartMachineID;

    @FXML
    private TextField addPartMax;

    @FXML
    private TextField addPartMin;

    @FXML
    private TextField addPartPrice;

    @FXML
    private RadioButton appPartFormOutsourcedRadio;

    @FXML
    private ToggleGroup partToggleGroup;

    @FXML
    private Label machineIdOrCompanyName;


    /**
     *method to change the label on the machineId or companyName depending on the selected radiobutton
     * @param event
     */
   @FXML
    void onSelectAddPartInHouse(ActionEvent event) {
        machineIdOrCompanyName.setText("Machine ID");
    }

    /**
     * method to change the label on the machineId or companyName depending on the selected radiobutton
     * @param event
     */
    @FXML
    void onSelectAddPartOutSourced(ActionEvent event) {
        machineIdOrCompanyName.setText("Company Name");

    }

    /**
     * On cancel button click exit scene to mainscreen scene
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCancelDirectToMain(ActionEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * on click of save button the added part is saved and added to tableview mainScreenPartTable
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSavePartDirectToMain(ActionEvent event) throws IOException {

        try {
            /* generating a randnom number to populate addPartId*/
            int id = (int) (Math.random() * 1000);

            /*getting values for the text field*/

            String name = addPartName.getText();
            int stock = Integer.parseInt(addPartInv.getText());
            double price = Double.parseDouble(addPartPrice.getText());
            int min = Integer.parseInt(addPartMin.getText());
            int max = Integer.parseInt(addPartMax.getText());

            String companyName;
            int machineId;
            /*Inventory between the min and max values*/

            if (max < min) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Max must be greater then Min");
                alert.showAndWait();
                return;

            }


            else if (stock < min || stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Stock must be in  between min and max");
                alert.showAndWait();
                return;


            }




            if (appPartFormOutsourcedRadio.isSelected()) {
                companyName = ((addPartMachineID.getText()));
                OutSourced addPart = new OutSourced(id, name, price, stock, min, max, companyName);
                Inventory.addPart(addPart);
            }

            if (addPartFormInHouseRadio.isSelected()) {
                machineId = Integer.parseInt(addPartMachineID.getText());
                InHouse addPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.addPart(addPart);
            }




            Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
            Scene scene = new Scene(addPart);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error");
            alert.setContentText("Incorrect value");
            alert.showAndWait();
            return;

        }
    }

    }