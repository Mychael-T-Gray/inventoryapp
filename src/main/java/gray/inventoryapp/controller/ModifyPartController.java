package gray.inventoryapp.controller;

import gray.inventoryapp.model.InHouse;
import gray.inventoryapp.model.OutSourced;
import gray.inventoryapp.model.Part;
import gray.inventoryapp.model.Inventory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifyPartController {

    @FXML
    private Button modifyPartCancelButton;

    @FXML
    private RadioButton modifyPartFormInHouseRadio;

    @FXML
    private RadioButton modifyPartFormOutsourcedRadio;

    @FXML
    private Button modifyPartFormSave;

    @FXML TextField modifyPartID;

    @FXML
    private TextField modifyPartName;

    @FXML
    private TextField modifyPartInv;

    @FXML
    private TextField modifyPartMachineID;

    @FXML
    private TextField modifyPartMax;

    @FXML
    private TextField modifyPartMin;

    @FXML
    private TextField modifyPartPrice;

    @FXML
    private ToggleGroup modifyToggleGroup;

    @FXML
    private TextField addPartMachineID;

    @FXML
    private Label MachineIdOrCompanyName;

    private int currentIndex = 0;

    /**
     * method to cancel modify part and return to the main screen
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
     *method to send part parameters from the main screen
     * RunTime Error: When I first wrote this method label MachineIdOrCompanyName would not change
     * with the corresponding radiobuttons modifyPartFormInHouseRadio or modifyPartFormOutsourcedRadio.
     * I had on action events declared in seperate methods, while this changed the part visually on the form it was not
     * actually associated of an instance of InHouse part or OutSourced part. I then moved the label
     * with method setText() inside my if instanceOf, or else if instanceOf. This had the desired
     * effect of changing the label when part was of type InHouse or OutSourced.
     * @param selectedIndex
     * @param part
     */
    public void partParemeters(int selectedIndex, Part part) {

        currentIndex = selectedIndex;

        if(part instanceof InHouse) {
            modifyPartFormInHouseRadio.setSelected(true);
            addPartMachineID.setText(String.valueOf(((InHouse) part).getMachineId()));
            MachineIdOrCompanyName.setText("Machine ID");
        }

        else if (part instanceof OutSourced){ modifyPartFormOutsourcedRadio.setSelected(true);
            addPartMachineID.setText(String.valueOf(((OutSourced) part).getCompanyName()));
            MachineIdOrCompanyName.setText("Company Name");


        }




        modifyPartID.setText(String.valueOf(part.getId()));
        modifyPartName.setText(part.getName());
        modifyPartInv.setText(String.valueOf(part.getStock()));
        modifyPartPrice.setText(String.valueOf(part.getPrice()));
        modifyPartMax.setText(String.valueOf(part.getMax()));
        modifyPartMin.setText(String.valueOf(part.getMin()));


    }

    /**
     * method to change label machineid or company name depending on selected radio button
     * @param event
     */
    @FXML
    public void onSelectModifyPartInHouse(ActionEvent event) {
        MachineIdOrCompanyName.setText("Machine ID");
    }


    /**
     * method to change label machineid or company name depending on selected radio button
     * @param event
     */
    @FXML
    public void OnSelectOutSourcedModifyPartRadio(ActionEvent event) {
        MachineIdOrCompanyName.setText("Company Name");
    }


    /**
     * method to save part changes and direct to main
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSavePartDirectToMain(ActionEvent event) throws IOException {

        try {
            int id = Integer.parseInt(modifyPartID.getText());
            String name = modifyPartName.getText();
            double price = Double.parseDouble(modifyPartPrice.getText());
            int stock = Integer.parseInt(modifyPartInv.getText());
            int min = Integer.parseInt(modifyPartMin.getText());
            int max = Integer.parseInt(modifyPartMax.getText());

            String companyName;
            int machineId;


            if (min > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Min must be less then max");
                alert.showAndWait();
                return;

            }

            else if(stock < min || stock > max){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Input Error");
                alert.setContentText("Stock must be between min and max");
                alert.showAndWait();
                return;

            }




            if(modifyPartFormInHouseRadio.isSelected()){
                machineId = Integer.parseInt(addPartMachineID.getText());
                InHouse updatedPart = new InHouse(id, name, price, stock, min, max, machineId);
                Inventory.updatePart(currentIndex, updatedPart);
            }

            if (modifyPartFormOutsourcedRadio.isSelected())
            {
                companyName = addPartMachineID.getText();
                OutSourced updatedPart = new OutSourced(id, name, price, stock, min, max, companyName);
                Inventory.updatePart(currentIndex, updatedPart);

            }
            Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
            Scene scene = new Scene(addPart);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setContentText("Inputed incorrect value. ");


        }
    }
}
