package gray.inventoryapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;



public class Inventory {

    /**
     * Observable list to store all parts, associated with mainscreen parts tableview
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * method to return all parts in Observable list allParts, for use in look-up part method
     * @return
     */
    public static ObservableList<Part> getAllParts() {

        return allParts;
    }

    /**
     * observable list for products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * method to add a new part to Observable list allParts
     * @param newPart
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * method to return all products in Observable list allProducts, for use in look up
     *     product method
     * @return
     */
    public static ObservableList<Product> getAllProducts() {

        return allProducts;
    }

    /**
     * method to add new product to Observable list allProducts
     * @param newProduct
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }


    /**
     * look up part by part id and return if found
     * @param partID
     * @return
     */
    public static Part lookupPart(int partID) {
        for (Part part : Inventory.getAllParts()) {
            while (part.getId() == partID) {
                return part;
            }
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Search Error");
        alert.setHeaderText("No matching part found");
        alert.showAndWait();
        return null;
    }


    /**
     * look up product by product Id and return if found
     * @param productId
     * @return
     */
    public static Product lookupProduct(int productId) {
        for (Product product : Inventory.getAllProducts()) {
            while (product.getId() == productId)
                return product;
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText("Product not found");
        alert.showAndWait();
        return null;
    }


    /**
     * look up parts by part name
     * @param partName
     * @return
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> PartName = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)) {
                PartName.add(part);
            }
        }
        return PartName;
    }


    /**
     * return products by part name
     * @param productName
     * @return
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> ProductName = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)) {
                ProductName.add(product);
            }
        }
        return ProductName;
    }


    /**
     * update part in Observable list allparts
     * @param index
     * @param selectedPart
     */
    public static void updatePart(int index, Part selectedPart) {

        allParts.set(index, selectedPart);
    }


    /**
     * update product in Observable list allProducts
     * @param index
     * @param selectedProduct
     */
    public static void updateProduct(int index, Product selectedProduct) {

        allProducts.set(index, selectedProduct);
    }


    /**
     * delete part in Observable list allparts
     * @param selectedPart
     * @return
     */
    public static boolean deletePart(Part selectedPart) {
        if (allParts.contains(selectedPart)) {
            allParts.remove(selectedPart);
            return true;
        } else {
            return false;
        }
    }


    /**
     * delete product in Observable list all products
     * @param selectedProduct
     * @return
     */
    public static boolean deleteProduct(Product selectedProduct) {
        if (allProducts.contains(selectedProduct)) {
            allProducts.remove(selectedProduct);
            return true;
        } else {
            return false;
        }
    }




}



