package inventory.view_controller;

import inventory.model.Inventory;
import static inventory.model.Inventory.getAllParts;
import static inventory.model.Inventory.getAllProducts;
import inventory.model.Part;
import inventory.model.Product;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MainController implements Initializable {
    
    
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
    private TextField searchPartTextField;
    @FXML
    private TextField searchProductTextField;
    @FXML 
    private Button exitButton;
    @FXML 
    private TableView<Product> productTable;
    @FXML
    private TableColumn<Product, Integer> productIDColumn;
    @FXML 
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    private static Part modifyPart;
    private static int modifyPartIndex;
    private static Product modifyProduct;
    private static int modifyProductIndex;

    
    // Method automatically ran when Main is opened, populates part & product tableviews
    @FXML
    @Override
    public void initialize(URL url, ResourceBundle rb){  
        partTable.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(cellData -> cellData.getValue().partIDProperty().asObject());
        partNameColumn.setCellValueFactory(cellData -> cellData.getValue().partNameProperty());
        partInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().partInventoryProperty().asObject());
        partPriceColumn.setCellValueFactory(cellData -> cellData.getValue().partPriceProperty().asObject()); 
        
        productTable.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(cellData -> cellData.getValue().productIDProperty().asObject());
        productNameColumn.setCellValueFactory(cellData -> cellData.getValue().productNameProperty());
        productInventoryColumn.setCellValueFactory(cellData -> cellData.getValue().productInventoryProperty().asObject());
        productPriceColumn.setCellValueFactory(cellData -> cellData.getValue().productPriceProperty().asObject());  
    }

    // Methods to reset part and product tables
    @FXML
    void resetPartTable(){
        partTable.setItems(Inventory.getAllParts());
        searchPartTextField.setText("");
        searchPartTextField.requestFocus();
    }
    @FXML
    void resetProductTable(){
        productTable.setItems(Inventory.getAllProducts());
        searchProductTextField.setText("");
    }
    
    // Method to search part table by part ID or part name
    @FXML
    public ObservableList<Part> searchPartList=FXCollections.observableArrayList();
    // Action when the user clicks search part button.
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
            }   
            if (found==false){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Part p: Inventory.getAllParts()){
                if(p.getPartName().equals(searchItem)){
                    found=true;            
                    searchPartList.clear();
                    searchPartList.add(p);
                    partTable.setItems(searchPartList);
                }
            }
            if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Part not found");
                alert.showAndWait();
            }
        }
    }
    
    // Method to search product table by product ID or product name
    @FXML
    public ObservableList<Product> searchProductList=FXCollections.observableArrayList();
    // Action when the user clicks search product.
    @FXML
    void SearchProductTable(ActionEvent event) {
        String searchItem=searchProductTextField.getText();
        boolean found=false;
        try{
            int itemNumber=Integer.parseInt(searchItem);
            for(Product p: Inventory.getAllProducts()){
                if(p.getProductID()==itemNumber){
                    found=true;
                    searchProductList.clear();
                    searchProductList.add(p);
                    productTable.setItems(searchProductList);
                }  
            }
            if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Product not found");
                alert.showAndWait();
            }
        }
        catch(NumberFormatException e){
            for(Product p: Inventory.getAllProducts()){
                if(p.getProductName().equals(searchItem)){
                    found=true;
                    searchProductList.clear();
                    searchProductList.add(p);
                    productTable.setItems(searchProductList);
                } 
            }
            if (found==false){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Product not found");
                alert.showAndWait();
            }
        }
    }
  
    // Deletes selected part from partTable 
    // Deletes selected product from productTable
    @FXML
    void handleDeletePartButton(ActionEvent event){
        Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmation Dialog");
        alertConfirm.setHeaderText("Are you sure you want to delete this part?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            if(partTable.getSelectionModel().getSelectedItem() != null){
                Part part = partTable.getSelectionModel().getSelectedItem();
                Inventory.deletePart(part);
            }else if(partTable.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("No Part Selected");
                alert.showAndWait();
            }
        }    
    }
    @FXML
    void handleDeleteProductButton(ActionEvent event){
        Alert alertConfirm = new Alert(AlertType.CONFIRMATION);
        alertConfirm.setTitle("Confirmation Dialog");
        alertConfirm.setHeaderText("Are you sure you want to delete this product?");
        Optional<ButtonType> result = alertConfirm.showAndWait();
        if (result.get() == ButtonType.OK){
            if(productTable.getSelectionModel().getSelectedItem() != null){
                Product product = productTable.getSelectionModel().getSelectedItem();
                Inventory.deleteProduct(product);
            }else if(productTable.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("No Product Selected");
                alert.showAndWait();
            }
        }
    }
    
    // When user clicks Add Part a new add part window will show
    @FXML
    void handleAddPartButton(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
            Parent root = (Parent) loader.load();
            Stage stage = new Stage();
            stage.setTitle("Add Part");
            stage.setScene(new Scene(root));
            stage.show();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // When user clicks Modify Part a new modify part window will show
    @FXML
    void handleModifyPartButton(ActionEvent event){
        try{
            if(partTable.getSelectionModel().getSelectedItem() != null){
                
                modifyPart = partTable.getSelectionModel().getSelectedItem();
                modifyPartIndex = getAllParts().indexOf(modifyPart);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Part");
                stage.setScene(new Scene(root));
                stage.show();
                ModifyPartController controller = loader.getController();
                Part part = partTable.getSelectionModel().getSelectedItem();
                controller.setPart(part);
                
            } else if(partTable.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("No Part Selected");
                alert.showAndWait();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // When user clicks Add Product a new add product window will pop
    @FXML
    void handleAddProductButton(ActionEvent event){
        if(Inventory.getAllParts().isEmpty()){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Error!");
            alert.setContentText("A product can not be added without parts!");
            alert.showAndWait();
        } else {
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Add Product");
                stage.setScene(new Scene(root));
                stage.show();
            } catch(Exception e){
                e.printStackTrace();
            }   
        }
    }
    
    // When user clicks Modify Product a new modify product window will pop
    @FXML
    void handleModifyProductButton(ActionEvent event){
        try{
            if(productTable.getSelectionModel().getSelectedItem() != null){
                modifyProduct = productTable.getSelectionModel().getSelectedItem();
                modifyProductIndex = getAllProducts().indexOf(modifyProduct);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
                Parent root = (Parent) loader.load();
                Stage stage = new Stage();
                stage.setTitle("Modify Product");
                stage.setScene(new Scene(root));
                stage.show();
                ModifyProductController controller = loader.getController();
                controller.setProduct(modifyProduct);
            } else if(productTable.getSelectionModel().getSelectedItem() == null){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("No Product Selected");
                alert.showAndWait();
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    // When the user clicks exit the application closes
    @FXML
    void handleExitButton(ActionEvent event){
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    
    // Methods to return product\part index, used to update in product\part controller
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }
    public static int productToModifyIndex(){
        return modifyProductIndex;
    }
}
