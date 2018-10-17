package inventory.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;


public class Inhouse extends Part{
    
    private final IntegerProperty partMachineID;
  
    // Constructor
    public Inhouse() {
        super();
        partMachineID = new SimpleIntegerProperty();
    }

    // Accessor
    public int getPartMachineID() {
        return this.partMachineID.get();
    }
    // Mutator
    public void setPartMachineID(int partMachineID) {
        this.partMachineID.set(partMachineID);
    }
   
    
    
}
