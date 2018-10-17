package inventory.model;

import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Product {
    
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private final IntegerProperty productID;
    private final StringProperty productName;
    private final IntegerProperty productInventory;
    private final DoubleProperty productPrice;
    private final IntegerProperty productMax;
    private final IntegerProperty productMin;

    
    // Constructor
    public Product(){
        productID = new SimpleIntegerProperty();
        productName = new SimpleStringProperty();
        productInventory = new SimpleIntegerProperty();
        productPrice = new SimpleDoubleProperty();
        productMax = new SimpleIntegerProperty();
        productMin = new SimpleIntegerProperty();
  
    }
    
 
    public ObservableList<Part> getAssociatedParts() {
        return associatedParts;
    }
    
    public void setAssociatedParts(ObservableList<Part> associatedParts){
        this.associatedParts = associatedParts;
    }
    
    
    public Part lookupAssociatedPart(int partIndex){
        return associatedParts.get(partIndex);
    }

    public void addAssociatedPart(Part part){
        associatedParts.add(part);
    }
    
    public boolean removeAssociatedPart(int partIndex){
        associatedParts.remove(partIndex);
        return true;
    }
   
    // Mutators
    public void setProductID(int productID){
        this.productID.set(productID);
    }
    public void setProductName(String productName){
        this.productName.set(productName);
    }
    public void setProductInventory(int productInventory){
        this.productInventory.set(productInventory);
    }
    public void setProductPrice(double productPrice){
        this.productPrice.set(productPrice);
    }
    public void setProductMax(int productMax){
        this.productMax.set(productMax);
    }
    public void setProductMin(int productMin){
        this.productMin.set(productMin);
    }
    
    // Accessors
    public int getProductID(){
        return this.productID.get();
    }
    public String getProductName(){
        return this.productName.get();
    }
    public int getProductInventory(){
        return this.productInventory.get();
    }
    public double getProductPrice(){
        return this.productPrice.get();
    }
    public int getProductMax(){
        return this.productMax.get();
    }
    public int getProductMin(){
        return this.productMin.get();
    } 
    
    public IntegerProperty productIDProperty(){
        return productID;
    }
    public StringProperty productNameProperty(){
        return productName;
    }
    public IntegerProperty productInventoryProperty(){
        return productInventory;
    }
    public DoubleProperty productPriceProperty(){
        return productPrice;
    }
}
