module gray.inventoryapp {
    requires javafx.controls;
    requires javafx.fxml;



    exports gray.inventoryapp.main;
    opens gray.inventoryapp.main to javafx.fxml;
    exports gray.inventoryapp.controller;
    opens gray.inventoryapp.controller to javafx.fxml;
    exports gray.inventoryapp.model;
    opens gray.inventoryapp.model to javafx.fxml;

}