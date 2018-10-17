package inventory.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Outsourced extends Part{
    
    private final StringProperty partCompanyName;
    
    // Constructor
    public Outsourced() {
        super();
        partCompanyName = new SimpleStringProperty();
    }
    // Accessor
    public String getCompanyName() {
        return this.partCompanyName.get();
    }
    // Mutator
    public void setCompanyName(String companyName) {
        this.partCompanyName.set(companyName);
    }
}