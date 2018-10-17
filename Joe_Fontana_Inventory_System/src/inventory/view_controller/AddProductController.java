/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view_controller;


import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.Product;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class AddProductController  {
    
    @FXML
    private TextField addProductIDField;
    @FXML
    private TextField addProductNameField;
    @FXML
    private TextField addProductInventoryField;
    @FXML
    private TextField addProductPriceField;
    @FXML 
    private TextField addProductMaxField;
    @FXML
    private TextField addProductMinField;
    @FXML 
    private Button addProductSaveButton;
    @FXML
    private Button addProductCancelButton;
    @FXML
    private TableView<Part> partTable;
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableView<Part> associatedPartTable;
    @FXML
    private TableColumn<Part, Integer> associatedPartID;
    @FXML
    private TableColumn<Part, String> associatedPartName;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventory;
    @FXML
    private TableColumn<Part, Double> associatedPartPrice;
    @FXML
    private TextField searchPartTextField;
    @FXML
    private TextField searchAssocPartTextField;
    Product product = new Product();
    private int productID;
    
    // Method ran automatically when page is opened
    @FXML
    public void initialize(){  
        updatePartTable();
        productID = Inventory.getCurrentProductID();
        addProductIDField.setText(Integer.toString(productID));
    }
    
    // Method to update part table information
    // Method to updated associated part table
    @FXML
    void updatePartTable(){
        partTable.setItems(Inventory.getAllParts());
    
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject()); 
    }
    
    @FXML
    void updateAssociatedPartTable(){
        associatedPartTable.setItems(product.getAssociatedParts());
        associatedPartID.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        associatedPartName.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        associatedPartInventory.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        associatedPartPrice.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject()); 
    }
    
    // Method to search part table by part ID or part name
    @FXML
    public ObservableList<Part> searchPartList=FXCollections.observableArrayList();
    // Action when the user clicks search part.
    @FXML
    void SearchPartTable(ActionEvent event) {
        String searchItem=searchPartTextField.getText();
        boolean found=false;
        try{
            int itemNumber=Integer.parseInt(searchItem);
            for(Part p: Inventory.getAllParts()){
                if(p.getPartID()==itemNumber){
                    found=true;
                    searchPartList.clear();
                    searchPartList.add(p);
                    partTable.setItems(searchPartList);
                }  
            } if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        } catch(NumberFormatException e){
            for(Part p: Inventory.getAllParts()){
                if(p.getPartName().equals(searchItem)){
                    found=true;
                
                    searchPartList.clear();
                    searchPartList.add(p);
                    partTable.setItems(searchPartList);
                }
            
            } if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }
    }
     
    // Method to search associated part table by part ID or part name
    @FXML
    public ObservableList<Part> searchAssocPartList=FXCollections.observableArrayList();
    // Action when the user clicks search part.
    @FXML
    void SearchAssocPartTable(ActionEvent event) {
        String searchItem=searchAssocPartTextField.getText();
        boolean found=false;
        try{
            int itemNumber=Integer.parseInt(searchItem);
            for(Part p: product.getAssociatedParts()){
                if(p.getPartID()==itemNumber){
                    found=true;
                    searchAssocPartList.clear();
                    searchAssocPartList.add(p);
                    associatedPartTable.setItems(searchAssocPartList);
                }  
            }
            if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        } catch(NumberFormatException e){
            for(Part p: product.getAssociatedParts()){
                if(p.getPartName().equals(searchItem)){
                    found=true;
                
                    searchAssocPartList.clear();
                    searchAssocPartList.add(p);
                    associatedPartTable.setItems(searchAssocPartList);
                }    
            }if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }
    }
    
    // Method to add associated part
    // Called when user clicks Add
    @FXML
    public void handleAdd(ActionEvent event) {
        
        Part p = partTable.getSelectionModel().getSelectedItem();
        if(partTable.getSelectionModel().getSelectedItem() != null){
            product.addAssociatedPart(p);
            updateAssociatedPartTable();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("No Part Selected");

            alert.showAndWait();
        }
    }
    
    // Deletes selected part from associatedPartTable     
    @FXML
    void handleDeleteAssocPartButton(ActionEvent event){
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmation Dialog");
        alertConfirm.setHeaderText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            if(associatedPartTable.getSelectionModel().getSelectedItem() != null){
            Part part = associatedPartTable.getSelectionModel().getSelectedItem();
            product.getAssociatedParts().remove(part);
            associatedPartTable.setItems(product.getAssociatedParts());
        }else if(partTable.getSelectionModel().getSelectedItem() == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error");
            alert.setContentText("No Part Selected");

            alert.showAndWait();
        }} else {
        }
    }
    
    // Closes out add product screen
    @FXML
    public void handleCancel(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmation Dialog");
        alertConfirm.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) addProductCancelButton.getScene().getWindow();
            stage.close();
        } else{
        }
    }
    
    /** 
     *  Action when the user clicks save product.
     *  @param event
     */
    @FXML
    void handleAddProductSave(ActionEvent event){
        String productName = addProductNameField.getText();
        String productInventory = addProductInventoryField.getText();
        String productPrice = addProductPriceField.getText();
        String productMax = addProductMaxField.getText();
        String productMin = addProductMinField.getText();
        if(isInputValid() == true){
            if(product.getAssociatedParts().isEmpty() == false){     
                try{
                    product.setProductID(productID);
                    product.setProductName(productName);
                    product.setProductInventory(Integer.parseInt(productInventory));
                    product.setProductPrice(Double.parseDouble(productPrice));
                    product.setProductMax(Integer.parseInt(productMax));
                    product.setProductMin(Integer.parseInt(productMin));         
                    product.setAssociatedParts(product.getAssociatedParts());
                    Inventory.addProduct(product);
                    Stage stage = (Stage) addProductSaveButton.getScene().getWindow();
                    stage.close(); 
                } catch(Exception e){
                    e.printStackTrace();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product must have at least 1 part!");
                alert.showAndWait();
            }
        }
    }
     
    // Validates user input in text fields.
    private boolean isInputValid() {
        String errorMessage = "";

        if (addProductNameField.getText() == null || addProductNameField.getText().length() == 0) {
            errorMessage += "No valid product name!\n"; 
        }
        if (addProductInventoryField.getText() == null || addProductInventoryField.getText().length() == 0) {
            errorMessage += "No valid product inventory!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(addProductInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product inventory (must be an integer)!\n"; 
            }
        }
        if (addProductPriceField.getText() == null || addProductPriceField.getText().length() == 0) {
            errorMessage += "No valid product price!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                double x = Double.parseDouble(addProductPriceField.getText());
                if(getAssocPartPrice() > x){
                    errorMessage += "Product Price cannot be less than associated parts total price!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid product price (must be a double)!\n"; 
            }
        }
        if (addProductMaxField.getText() == null || addProductMaxField.getText().length() == 0) {
            errorMessage += "No valid product inventory!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(addProductMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product max (must be an integer)!\n"; 
            }
        }
        if (addProductMinField.getText() == null || addProductMinField.getText().length() == 0) {
            errorMessage += "No valid product min!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(addProductMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product min (must be an integer)!\n"; 
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
    
    // method to add up assoc parts total price
    private double getAssocPartPrice(){
        double total = 0;
        for(Part part: product.getAssociatedParts()){
            double price = part.getPartPrice();
            total = total + price;
        }
        return total;
    }
}
 
    
    
   

