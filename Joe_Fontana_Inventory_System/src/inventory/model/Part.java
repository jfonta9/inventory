
package inventory.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public abstract class Part {
    
    private final IntegerProperty partID;
    private final StringProperty partName;
    private final IntegerProperty partInventory;
    private final DoubleProperty partPrice;
    private final IntegerProperty partMax;
    private final IntegerProperty partMin;
    
    // Constructor
    public Part(){
        partID = new SimpleIntegerProperty();
        partName = new SimpleStringProperty();
        partInventory = new SimpleIntegerProperty();
        partPrice = new SimpleDoubleProperty();
        partMax = new SimpleIntegerProperty();
        partMin = new SimpleIntegerProperty();
    }
    
    // Mutators
    public void setPartID(int partID){
        this.partID.set(partID);
    }
    public void setPartName(String partName){
        this.partName.set(partName);
    }
    public void setPartInventory(int partInventory){
        this.partInventory.set(partInventory);
    }
    public void setPartPrice(double partPrice){
        this.partPrice.set(partPrice);
    }
    public void setPartMax(int partMax){
        this.partMax.set(partMax);
    }
    public void setPartMin(int partMin){
        this.partMin.set(partMin);
    }
    
    // Accessors
    public Integer getPartID(){
        return this.partID.get();
    }
    public String getPartName(){
        return this.partName.get();
    }
    public Integer getPartInventory(){
        return this.partInventory.get();
    }
    public Double getPartPrice(){
        return this.partPrice.get();
    }
    public Integer getPartMax(){
        return this.partMax.get();
    }
    public Integer getPartMin(){
        return this.partMin.get();
    } 
    
    public IntegerProperty partIDProperty(){
        return partID;
    }
    public StringProperty partNameProperty(){
        return partName;
    }
    public IntegerProperty partInventoryProperty(){
        return partInventory;
    }
    public DoubleProperty partPriceProperty(){
        return partPrice;
    }
    public IntegerProperty partMaxProperty(){
        return partMax;
    }
    public IntegerProperty partMinProperty(){
        return partMin;
    }
}
