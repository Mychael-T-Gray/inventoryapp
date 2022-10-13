package gray.inventoryapp.controller;

import gray.inventoryapp.model.Part;
import gray.inventoryapp.model.Product;
import gray.inventoryapp.model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyProductController implements Initializable {

    public ObservableList<Part> partsAssociatedWthProductList = FXCollections.observableArrayList();
    @FXML
    public TableView<Part> modifyProductTopTable;
    @FXML
    public TableView<Part> associetedPartTableModifyProductScreenBottom;

    @FXML
    private Button addModifyProductButton;

    @FXML
    private Button addModifyProductSaveButton;

    @FXML
    private Button cancelAModifyProductButton;

    @FXML
    private TableColumn<?, ?> invLevelModifyProductCol;

    @FXML
    private TableColumn<?, ?> inventoryLevelModifyAssociatedProductCol;

    @FXML
    private TextField modifyProductIdTextField;

    @FXML
    private TextField modifyProductInvTextField;

    @FXML
    private TextField modifyProductMaxTextField;

    @FXML
    private TextField modifyProductMinTextField;

    @FXML
    private TextField modifyProductNameTextField;

    @FXML
    private TextField modifyProductPriceTextField;

    @FXML
    private TextField modifyProductSearchArea;

    @FXML
    private TableColumn<?, ?> partIdModifyProductCol;

    @FXML
    private TableColumn<?, ?> partIdModifyAssociatedProductCol;

    @FXML
    private TableColumn<?, ?> partNameModifyProductCol;

    @FXML
    private TableColumn<?, ?> partNameModifyAssociatedProductCol;

    @FXML
    private TableColumn<?, ?> priceModifyProductCol;

    @FXML
    private TableColumn<?, ?> priceModifyAssociatedProduct;

    @FXML
    private Button removeModifyAssociatedPartButton;
    /*method to cancel modiy product and exit to main form*/
    @FXML
    void onClickModifyCancelAddProduct(ActionEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    private int currentIndex = 0;

    public void productParameters(int selectedIndex, Product product) {

        currentIndex = selectedIndex;

        modifyProductIdTextField.setText(String.valueOf(product.getId()));
        modifyProductNameTextField.setText(product.getName());
        modifyProductInvTextField.setText(String.valueOf(product.getStock()));
        modifyProductPriceTextField.setText(String.valueOf(product.getPrice()));
        modifyProductMaxTextField.setText(String.valueOf(product.getMax()));
        modifyProductMinTextField.setText(String.valueOf(product.getMin()));

        partsAssociatedWthProductList.addAll(product.getAllAssociatedParts());
    }

    public void initialize (URL url, ResourceBundle resourceBundle){
        modifyProductTopTable.setItems(Inventory.getAllParts());
        partIdModifyProductCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameModifyProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelModifyProductCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceModifyProductCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /*populate bottom table of associated parts*/

        associetedPartTableModifyProductScreenBottom.setItems(partsAssociatedWthProductList);
        partIdModifyAssociatedProductCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameModifyAssociatedProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelModifyAssociatedProductCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceModifyAssociatedProduct.setCellValueFactory(new PropertyValueFactory<>("price"));






    }
    @FXML
    void modifyProductPartSearchOnEnter(ActionEvent event) throws IOException {
        String modifyProductSearch = modifyProductSearchArea.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(modifyProductSearch);
        try {
            while (searchResults.size() == 0) {
                int id = Integer.parseInt(modifyProductSearch);
                searchResults.add(Inventory.lookupPart(id));

            }
            modifyProductTopTable.setItems(searchResults);
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setContentText("No part found");
            alert.showAndWait();
        }

    }

    @FXML
    void onClickAddModifyProduct(ActionEvent event) {
        Part selectedPart = modifyProductTopTable.getSelectionModel().getSelectedItem();
        partsAssociatedWthProductList.add(selectedPart);
        associetedPartTableModifyProductScreenBottom.setItems(partsAssociatedWthProductList);

    }


    @FXML
     void onClickModifySaveAddProduct (ActionEvent event) throws IOException{
    try {
        int id = Integer.parseInt(modifyProductIdTextField.getText());
        String name = modifyProductNameTextField.getText();
        int stock = Integer.parseInt(modifyProductInvTextField.getText());
        double price = Double.parseDouble(modifyProductPriceTextField.getText());
        int max = Integer.parseInt(modifyProductMaxTextField.getText());
        int min = Integer.parseInt(modifyProductMinTextField.getText());


        if (min > max) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Inventory Error");
            error.setContentText("Max must be greater then min");
            error.show();
            return;
        }

       else if (stock > max || stock < min){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Inventory Error");
            error.setContentText("Inventory must be between min and max");
            error.show();
            return;
        }


        Product updatedProduct = new Product(id,name,price,stock,min,max);
        Inventory.updateProduct(currentIndex, updatedProduct);

        for (Part part: partsAssociatedWthProductList){
            if ( part != partsAssociatedWthProductList)
                updatedProduct.addAssoiciatedPart(part);
        }
        Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    catch (NumberFormatException e){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Error");
        alert.setContentText("Input error");
        alert.showAndWait();
        return;

    }
    }

    @FXML
    void onClickRemoveModifyAssociatedPart(ActionEvent event) {
       Part selectedPart =  associetedPartTableModifyProductScreenBottom.getSelectionModel().getSelectedItem();


        try{
            if(partsAssociatedWthProductList.contains(selectedPart)) {
                Product.deleteAssociatedPart(selectedPart);
                partsAssociatedWthProductList.remove(selectedPart);
                associetedPartTableModifyProductScreenBottom.setItems(partsAssociatedWthProductList);
            }


        }

        catch (Exception e){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setContentText("Select a part to be removed");
        }
    }

}
