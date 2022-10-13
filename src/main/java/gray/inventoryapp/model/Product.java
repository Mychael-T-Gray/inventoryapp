package gray.inventoryapp.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {





    public static ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }


    /**
     * Id getter
     * @return
     */
    public int getId() {
        return id;
    }

    /**
     * id setter
     * @param id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * name getter
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * name setter
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * price getter
     * @return
     */
    public double getPrice() {
        return price;
    }

    /**
     * price setter
     * @param price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * stock getter
     * @return
     */
    public int getStock() {
        return stock;
    }

    /**
     * stock setter
     * @param stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * min getter
     * @return
     */
    public int getMin() {
        return min;
    }

    /**
     * min setter
     * @param min
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * max getter
     * @return
     */
    public int getMax() {
        return max;
    }

    /**
     * max setter
     * @param max
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * add part to Observable list associated parts
     * @param part
     */
    public void addAssoiciatedPart(Part part) {
     associatedParts.add(part);
    }

    /**
     * delete part from Observable list associated part
     * @param selectedAssociatedPart
     */
    public static void deleteAssociatedPart(Part selectedAssociatedPart){
        associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * return associated parts from getAllAssociatedParts
     * @return
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }


}
