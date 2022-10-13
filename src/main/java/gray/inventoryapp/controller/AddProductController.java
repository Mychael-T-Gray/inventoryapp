package gray.inventoryapp.controller;

import gray.inventoryapp.model.Inventory;
import gray.inventoryapp.model.Part;
import gray.inventoryapp.model.Product;
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

public class AddProductController implements Initializable {



    private final ObservableList <Part> partsAssociatedWthProductList = FXCollections.observableArrayList();

    @FXML
    public TableView <Part> associatedProductsAddProductBottomTable;
    @FXML
    private TableView <Part> addProductsTopTable;

    @FXML
    private TextField AddProductSearchArea;

    @FXML
    private Button addProductButtonProductForm;

    @FXML
    private TextField addProductIdTextField;

    @FXML
    private TextField addProductInvTextField;

    @FXML
    private TextField addProductMaxTextField;

    @FXML
    private TextField addProductMinTextField;

    @FXML
    private TextField addProductNameTextField;

    @FXML
    private TextField addProductPriceTextField;

    @FXML
    private Button addProductSaveButton;

    @FXML
    private Button cancelAddProductButton;

    @FXML
    private TableColumn<?, ?> invLevelAddProductCol;

    @FXML
    private TableColumn<?, ?> inventoryLevelAssociatedProductCol;

    @FXML
    private TableColumn<?, ?> partIdAddProductCol;

    @FXML
    private TableColumn<?, ?> partIdAssociatedProductCol;

    @FXML
    private TableColumn<?, ?> partNameAddProductCol;

    @FXML
    private TableColumn<?, ?> partNameAssociatedProductCol;

    @FXML
    private TableColumn<?, ?> priceAddProductCol;

    @FXML
    private TableColumn<?, ?> priceAssociatedProduct;

    @FXML
    private Button removeAssociatedPartButton;

    /**
     * method to search for products by id number or name/partial name
     * @param event
     */
    @FXML
    void addProductPartSearchOnEnter(ActionEvent event) {
        String addProductSearch = AddProductSearchArea.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(addProductSearch);
        try {
            while (searchResults.size() == 0) {
                int id = Integer.parseInt(addProductSearch);
                searchResults.add(Inventory.lookupPart(id));

            }
            addProductsTopTable.setItems(searchResults);
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Search Error");
            alert.setContentText("No part found");
            alert.showAndWait();
        }

    }

    /**
     * method to add parts to tableview associatedProductsAddProductBottomTable when they are associated with a product
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickAddProduct(ActionEvent event) throws IOException {
        Part selectedPart = addProductsTopTable.getSelectionModel().getSelectedItem();
        partsAssociatedWthProductList.add(selectedPart);
        associatedProductsAddProductBottomTable.setItems(partsAssociatedWthProductList);

    }


    /**
     * method to cancel add product and return to main screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickCancelAddProduct(ActionEvent event) throws IOException {
        Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * method to remove associated part from tableview associatedProductsAddProductBottomTable
     * @param event
     */
    @FXML
    void onClickRemoveAssociatedPart(ActionEvent event) {
       Part selectedPart = associatedProductsAddProductBottomTable.getSelectionModel().getSelectedItem();
       if (partsAssociatedWthProductList.contains(selectedPart))
       {
           partsAssociatedWthProductList.remove(selectedPart);
           associatedProductsAddProductBottomTable.setItems(partsAssociatedWthProductList);
       }
    }

    /**
     * method to receive user input from textfields about paramaters of a new product and add them to product inventory
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickSaveAddProduct(ActionEvent event) throws IOException {
        try {
            int id = (int) (Math.random() * 10000);
            String name = addProductNameTextField.getText();
            double price = Double.parseDouble(addProductPriceTextField.getText());
            int stock = Integer.parseInt(addProductInvTextField.getText());
            int min = Integer.parseInt(addProductMinTextField.getText());
            int max = Integer.parseInt(addProductMaxTextField.getText());

             if (min >= max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Min must be less then max");
                alert.showAndWait();
                return;
            }

            else if (stock < min|| stock > max) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Inventory Error");
                alert.setContentText("Inventory level must be between min and max");
                alert.showAndWait();
                return;
            }



            Product product = new Product(id, name, price, stock, min, max);

            for (Part part : partsAssociatedWthProductList) {
                if (part != partsAssociatedWthProductList)
                    product.addAssoiciatedPart(part);
            }

            Inventory.getAllProducts().add(product);

            Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/MainScreen.fxml"));
            Scene scene = new Scene(addPart);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }   catch(NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Input Error.");
            alert.setContentText("Incorrect input value.");
            alert.showAndWait();
            return;
        }
    }


    /**
     * method to populate tableview addProductsTopTable with products and associatedProductsAddProductBottomTable with associated parts
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addProductsTopTable.setItems(Inventory.getAllParts());
        partIdAddProductCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAddProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelAddProductCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAddProductCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        /*add parts to assiated parts bottom table on add product form*/

        associatedProductsAddProductBottomTable.setItems(partsAssociatedWthProductList);
        partIdAssociatedProductCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        partNameAssociatedProductCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        inventoryLevelAssociatedProductCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceAssociatedProduct.setCellValueFactory(new PropertyValueFactory<>("price"));


    }


}
