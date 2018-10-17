package inventory.view_controller;



import inventory.model.Inhouse;
import inventory.model.Inventory;
import inventory.model.Outsourced;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;




public class AddPartController implements Initializable{
    
    @FXML 
    private RadioButton inHouseRadio;
    @FXML 
    private RadioButton outSourcedRadio;
    @FXML 
    private Label toggleLabel;
    @FXML
    private TextField addPartIDField;
    @FXML
    private TextField addPartNameField;
    @FXML
    private TextField addPartInventoryField;
    @FXML
    private TextField addPartPriceField;
    @FXML 
    private TextField addPartMaxField;
    @FXML
    private TextField addPartMinField;
    @FXML
    private TextField addPartInOrOutField;
    @FXML
    private Button addPartCancelButton;
    @FXML 
    private Button addPartSaveButton;
    @FXML
    private Label reminderLabel;
    private boolean isInhouse;
    private int partID;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        partID = Inventory.getCurrentPartID();
        addPartIDField.setText(Integer.toString(partID));
    }
    
    /**
     * Displays Machine ID label when Inhouse radio button is selected,
     * Displays Company Name label when Outsourced radio button is selected
     * @param event 
     */
    @FXML
    void handleAddPartRadio(ActionEvent event){
        try{
            if(inHouseRadio.isFocused()){
                reminderLabel.setVisible(false);
                toggleLabel.setText("Machine ID");
                isInhouse = true;
            } else if(outSourcedRadio.isFocused()){
                reminderLabel.setVisible(false);
                toggleLabel.setText("Company Name");
                isInhouse = false;
            }
        } catch(Exception e){
            e.printStackTrace();
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
            Stage stage = (Stage) addPartCancelButton.getScene().getWindow();
            stage.close();
        }
        else{
        }
    }
   
    /**
     * Action when the user clicks save part.
     * @param event 
     */
    @FXML
    void handleAddPartSave(ActionEvent event){
        String partName = addPartNameField.getText();
        String partInventory = addPartInventoryField.getText();
        String partPrice = addPartPriceField.getText();
        String partMax = addPartMaxField.getText();
        String partMin = addPartMinField.getText();
        String toggle = addPartInOrOutField.getText();
        if(isInputValid() == true){
            try{
                if(inHouseRadio.isSelected()){
                    Inhouse inhouse = new Inhouse();

                    inhouse.setPartID(partID);
                    inhouse.setPartName(partName);
                    inhouse.setPartInventory(Integer.parseInt(partInventory));
                    inhouse.setPartPrice(Double.parseDouble(partPrice));
                    inhouse.setPartMax(Integer.parseInt(partMax));
                    inhouse.setPartMin(Integer.parseInt(partMin));
                    inhouse.setPartMachineID(Integer.parseInt(toggle));
                
                    Inventory.addPart(inhouse);
                } else if(outSourcedRadio.isSelected()){
                    Outsourced outsourced = new Outsourced();
                
                    outsourced.setPartID(partID);
                    outsourced.setPartName(partName);
                    outsourced.setPartInventory(Integer.parseInt(partInventory));
                    outsourced.setPartPrice(Double.parseDouble(partPrice));
                    outsourced.setPartMax(Integer.parseInt(partMax));
                    outsourced.setPartMin(Integer.parseInt(partMin));
                    outsourced.setCompanyName(toggle);
                
                    Inventory.addPart(outsourced);
                }
                Stage stage = (Stage) addPartSaveButton.getScene().getWindow();
                stage.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        } 
    }
   
    // Validates user input in text fields.
    private boolean isInputValid() {
        String errorMessage = "";
        
        // Ensure either In-house or Outsourced radio buttons are selected
        if (inHouseRadio.isSelected()){
            if (addPartInOrOutField.getText() == null || addPartInOrOutField.getText().length() == 0) {
                errorMessage += "No valid part machine ID!\n"; 
            }else {
                // try to parse the part id into an int.
                try {
                    Integer.parseInt(addPartInOrOutField.getText());
                } catch (NumberFormatException e) {
                    errorMessage += "No valid part machine ID (must be an integer)!\n"; 
                }
            }
        }else if(outSourcedRadio.isSelected()){
            // Ensure part company name is not empty
            if (addPartInOrOutField.getText() == null || addPartInOrOutField.getText().length() == 0){
                errorMessage += "No valid part company name!\n";
            }
        } else {
            errorMessage += "Please select In-house or Outsourced!\n";
        }
        
        // Ensure part name is not empty
        if (addPartNameField.getText() == null || addPartNameField.getText().length() == 0){
            errorMessage += "No valid part name!\n";
        }
        
        // Ensure part inventory field is an integer and not empty
        if (addPartInventoryField.getText() == null || addPartInventoryField.getText().length() == 0) {
            errorMessage += "No valid part inventory!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Integer.parseInt(addPartInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part inventory (must be an integer)!\n"; 
            }
        }
        
        // Ensure part price field is a double and not empty
        if (addPartPriceField.getText() == null || addPartPriceField.getText().length() == 0) {
            errorMessage += "No valid part price!\n"; 
        }else {
            // try to parse the part price into a double.
            try {
                Double.parseDouble(addPartPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part price (must be a double)!\n"; 
            }
        }
        
        // Ensure part max field is an integer and not empty
        if (addPartMaxField.getText() == null || addPartMaxField.getText().length() == 0) {
            errorMessage += "No valid part max!\n"; 
        }else {
            // try to parse the part max into an int.
            try {
                Integer.parseInt(addPartMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part max (must be an integer)!\n"; 
            }
        }
        
        // Ensure part max field is an integer and not empty
        if (addPartMinField.getText() == null || addPartMinField.getText().length() == 0) {
            errorMessage += "No valid part min!\n"; 
        }else {
            // try to parse the part min into an int.
            try {
                Integer.parseInt(addPartMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part min (must be an integer)!\n"; 
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
}
  
        
    
        
  