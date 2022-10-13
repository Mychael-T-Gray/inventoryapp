package gray.inventoryapp.controller;

import gray.inventoryapp.model.Part;
import gray.inventoryapp.model.Product;
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
import gray.inventoryapp.model.Inventory;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {

    @FXML
    private TextField mainScreenProductsSearchBox;
    @FXML
    private Button deletePartButton;

    @FXML
    private Button exitAppMainScreenButton;

    @FXML
    private Button mainScreenDeleteProductForm;

    @FXML
    private Button mainScreenModifyProductButton;

    @FXML
    private TableColumn<Part, Integer> mainScreenPartInvLevelCol;

    @FXML
    private TableColumn<Part, Integer> mainScreenPartIdCol;

    @FXML
    private TableColumn<Part, String> mainScreenPartNameCol;

    @FXML
    private TableColumn<Part, Double> mainScreenPartsPriceColumn;

    @FXML
    private TableView<Part> mainScreenPartTable;

    @FXML
    private TextField mainScreenPartsSearchBox;


    @FXML
    private TableView<Product> mainScreenProductTableView;

    @FXML
    private TableColumn<Product, Integer> mainScreenProductIdCol;

    @FXML
    private TableColumn<Product, Integer> mainScreenProductInventoryCol;

    @FXML
    private TableColumn<Product, String> mainScreenProductNameCol;

    @FXML
    private TableColumn<Product, Double> mainScreenProductPriceCol;

    @FXML
    private Button mainScreenAddProductButton;

    @FXML
    private Button modifyPartFormButton;

    @FXML
    private Button openAddPartButton;

    /**
     * Method to search produxts on the mainscreen by id number or name/partialname
     * @param event
     */
    @FXML
    void searchMainScreenProducts(ActionEvent event){

        String searchProductText = mainScreenProductsSearchBox.getText();
        ObservableList<Product> searchProductsResults = Inventory.lookupProduct(searchProductText);
        try{
            while (searchProductsResults.size() == 0) {
                int productid = Integer.parseInt(searchProductText);
                searchProductsResults.add(Inventory.lookupProduct(productid));
            }
            mainScreenProductTableView.setItems(searchProductsResults);
        }
        catch (NumberFormatException e){
            Alert partNotFound = new Alert(Alert.AlertType.ERROR);
            partNotFound.setTitle("Search Error");
            partNotFound.setContentText("Product was not found.");
            partNotFound.showAndWait();
        }
    }


    /**
     * method to search parts on the mainscreen by id number or name/partial name
     * @param event
     */
    @FXML
    void mainScreenOnClickPartsSearch(ActionEvent event) {
     String searchPartText = mainScreenPartsSearchBox.getText();
        ObservableList<Part> searchResults = Inventory.lookupPart(searchPartText);
        try{
            while (searchResults.size() == 0) {
                int partId = Integer.parseInt(searchPartText);
                searchResults.add(Inventory.lookupPart(partId));
            }
            mainScreenPartTable.setItems(searchResults);
        }
        catch (NumberFormatException e){
            Alert partNotFound = new Alert(Alert.AlertType.ERROR);
            partNotFound.setTitle("Search Error");
            partNotFound.setContentText("Part was not found.");
            partNotFound.showAndWait();
        }
    }

    @FXML
     void onClickDeleteSelectedPart(ActionEvent event) {
        Part selectedPart = mainScreenPartTable.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm delete part");
        alert.setContentText("Do you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK)
        {
        Inventory.deletePart(selectedPart);}
        else if (result.get() == ButtonType.CANCEL){
            return;
        }
    }


    /**
     * method for button on click exit the app
     * @param event
     */
    @FXML
    void onClickExitApp(ActionEvent event) {
        Stage stage = (Stage) exitAppMainScreenButton.getScene().getWindow();
        stage.close();


    }

    /**
     * method to open addpart form when the add button under the parts table on the main screen is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickOpenAddPartForm(ActionEvent event) throws IOException {

        Parent addPart = FXMLLoader.load(getClass().getResource("/gray/inventoryapp.view/AddPart.fxml"));
        Scene scene = new Scene(addPart);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    /**
     * method to open add product form when the add product button is clicked
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickOpenAddProductForm(ActionEvent event) throws IOException {
        Parent addProduct = FXMLLoader.load((getClass().getResource("/gray/inventoryapp.view/AddProduct.fxml")));
        Scene scene = new Scene(addProduct);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    /**
     * method to delete products from mainScreenProductTableView when there is no asscoieted part
     * @param event
     */
    @FXML
    void onClickOpenDeleteProductForm(ActionEvent event) {

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation needed");
            alert.setContentText("Do you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();

            Product productToBeDeleted = mainScreenProductTableView.getSelectionModel().getSelectedItem();

            if (productToBeDeleted.getAllAssociatedParts().size()  == 0 && result.get() == ButtonType.OK)
            {Inventory.deleteProduct(productToBeDeleted);}

            else if(productToBeDeleted.getAllAssociatedParts().size() > 0  && result.get() == ButtonType.OK){
                Alert cantDeleteAlert = new Alert(Alert.AlertType.ERROR);
                cantDeleteAlert.setTitle("Cannot delete");
                cantDeleteAlert.setContentText("Cannot delete Product with associated parts");
                cantDeleteAlert.showAndWait();
                return;
            }

        } catch (Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error deleting product.");
        alert.setContentText("Product was not deleted.");
        alert.showAndWait();


        }
    }

    /**
     * method to open modify part form from the main screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickOpenModifyPartForm(ActionEvent event) throws IOException{

      try {
          FXMLLoader loader = new FXMLLoader();
          loader.setLocation(getClass().getResource("/gray/inventoryapp.view/ModifyPart.fxml"));
          loader.load();

          ModifyPartController APController = loader.getController();
          APController.partParemeters(mainScreenPartTable.getSelectionModel().getSelectedIndex(),
                  mainScreenPartTable.getSelectionModel().getSelectedItem());


          Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
          Parent scene = loader.getRoot();
          stage.setScene(new Scene(scene));
          stage.showAndWait();
      }

      catch (NullPointerException e){
          Alert alert = new Alert(Alert.AlertType.ERROR);
          alert.setTitle("Selection Error");
          alert.setContentText("Select a part.");
          alert.show();

        }

    }

    /**
     * method to open modify product form from the main screen
     * @param event
     * @throws IOException
     */
    @FXML
    void onClickOpenModifyProductForm(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/gray/inventoryapp.view/ModifyProduct.fxml"));
            loader.load();

            ModifyProductController MPController = loader.getController();
            MPController.productParameters(mainScreenProductTableView.getSelectionModel().getSelectedIndex(),
                    mainScreenProductTableView.getSelectionModel().getSelectedItem());


            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.showAndWait();
        }

        catch (NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Selection Error");
            alert.setContentText("Select a part.");
            alert.show();

        }

    }

    /**
     * populate parts and product tables on the main screen
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainScreenPartTable.setItems(Inventory.getAllParts());
        mainScreenPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainScreenPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainScreenPartsPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainScreenPartInvLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        mainScreenProductTableView.setItems(Inventory.getAllProducts());
        mainScreenProductIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        mainScreenProductNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        mainScreenProductPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        mainScreenProductInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }


    }

