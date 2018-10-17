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
public class ModifyProductController {
    
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
    private TableColumn<Part, Integer> associatedPartIDColumn;
    @FXML 
    private TableColumn<Part, String> associatedPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> associatedPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> associatedPartPriceColumn;
    @FXML
    private TextField modifyProductIDField;
    @FXML
    private TextField modifyProductNameField;
    @FXML
    private TextField modifyProductInventoryField;
    @FXML
    private TextField modifyProductPriceField;
    @FXML
    private TextField modifyProductMaxField;
    @FXML
    private TextField modifyProductMinField;
    @FXML
    private Button modifyProductSaveButton;
    @FXML
    private TextField searchPartTextField;
    @FXML
    private TextField searchAssocPartTextField;
    @FXML
    private Button modifyProductCancelButton;
    private Product product;
    int productIndex = MainController.productToModifyIndex();
    
    // Method MainController uses to set the product to be modified
    public void setProduct(Product product){
        this.product = product;
        modifyProductIDField.setText(Integer.toString(product.getProductID()));
        modifyProductNameField.setText(product.getProductName());
        modifyProductInventoryField.setText(Integer.toString(product.getProductInventory()));
        modifyProductPriceField.setText(Double.toString(product.getProductPrice()));
        modifyProductMaxField.setText(Integer.toString(product.getProductMax()));
        modifyProductMinField.setText(Integer.toString(product.getProductMin())); 
        updateAssociatedPartTable();
    }
   
    // Method ran automatically when page is opened
    @FXML
    public void initialize(){
        updatePartTable();
    }
    
    // Method to update part table information
    // Method to update product table information
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
        associatedPartIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        associatedPartNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        associatedPartInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        associatedPartPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject()); 
    }
    
    // Method to search part table by part ID or part Name
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
            }if (found==false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }catch(NumberFormatException e){
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
            } if (found==false){
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
            } if (found==false){
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
        product.addAssociatedPart(p);
        updateAssociatedPartTable();
    }
    
    // Deletes selected part from associated part table
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
    
    // Closes add part screen
    @FXML
    public void handleCancel(ActionEvent event) {
        Alert alertConfirm = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmation Dialog");
        alertConfirm.setHeaderText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            Stage stage = (Stage) modifyProductCancelButton.getScene().getWindow();
            stage.close();
        }else{
        }
    }
    
    /** 
     *  Action when the user clicks save product.
     *  @param event
     */
    @FXML
    void handleModifyProductSave(ActionEvent event){
        String productID = modifyProductIDField.getText();
        String productName = modifyProductNameField.getText();
        String productInventory = modifyProductInventoryField.getText();
        String productPrice = modifyProductPriceField.getText();
        String productMax = modifyProductMaxField.getText();
        String productMin = modifyProductMinField.getText();      
        if(isInputValid() == true){
            if(product.getAssociatedParts().isEmpty() == false){
                try{    
                    product.setProductID(Integer.parseInt(productID));
                    product.setProductName(productName);
                    product.setProductInventory(Integer.parseInt(productInventory));
                    product.setProductPrice(Double.parseDouble(productPrice));
                    product.setProductMax(Integer.parseInt(productMax));
                    product.setProductMin(Integer.parseInt(productMin));  
                    product.setAssociatedParts(product.getAssociatedParts());
                    Inventory.updateProduct(productIndex, product);
                    Stage stage = (Stage) modifyProductSaveButton.getScene().getWindow();
                    stage.close(); 
                } catch(Exception e){
                    e.printStackTrace();
                }
            } else {
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

        if (modifyProductIDField.getText() == null || modifyProductIDField.getText().length() == 0) {
            errorMessage += "No valid product ID!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(modifyProductIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid productt ID (must be an integer)!\n"; 
            }
        }
        if (modifyProductNameField.getText() == null || modifyProductNameField.getText().length() == 0) {
            errorMessage += "No valid product name!\n"; 
        }
        if (modifyProductInventoryField.getText() == null || modifyProductInventoryField.getText().length() == 0) {
            errorMessage += "No valid product inventory!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(modifyProductInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product inventory (must be an integer)!\n"; 
            }
        }
        if (modifyProductPriceField.getText() == null || modifyProductPriceField.getText().length() == 0) {
            errorMessage += "No valid product price!\n"; 
        }else {
            // try to parse the product id into an int.
            double x = Double.parseDouble(modifyProductPriceField.getText());
            try {
                if(getAssocPartPrice() > x){
                    errorMessage += "Product Price cannot be less than associated parts total price!\n";
                }
            } catch (NumberFormatException e) {
                errorMessage += "No valid product price (must be a double)!\n"; 
            }
        }
        if (modifyProductMaxField.getText() == null || modifyProductMaxField.getText().length() == 0) {
            errorMessage += "No valid product inventory!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(modifyProductMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid product max (must be an integer)!\n"; 
            }
        }
        if (modifyProductMinField.getText() == null || modifyProductMinField.getText().length() == 0) {
            errorMessage += "No valid product min!\n"; 
        }else {
            // try to parse the product id into an int.
            try {
                Integer.parseInt(modifyProductMinField.getText());
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
 