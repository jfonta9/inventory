/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inventory.view_controller;

import inventory.model.Inhouse;
import inventory.model.Inventory;
import inventory.model.Outsourced;
import inventory.model.Part;
import static inventory.view_controller.MainController.partToModifyIndex;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author root
 */
public class ModifyPartController {
        
    @FXML 
    private RadioButton inHouseRadio;
    @FXML 
    private RadioButton outSourcedRadio;
    @FXML 
    private Label modifyPartToggleLabel;
    @FXML
    private TextField modifyPartIDField;
    @FXML
    private TextField modifyPartNameField;
    @FXML
    private TextField modifyPartInventoryField;
    @FXML
    private TextField modifyPartPriceField;
    @FXML
    private TextField modifyPartMaxField;
    @FXML
    private TextField modifyPartMinField;
    @FXML
    private TextField modifyToggleTextField;
    @FXML
    private Button modifyPartSaveButton;
    @FXML
    private Button modifyPartCancelButton;
    private Part part;
    int partIndex = partToModifyIndex();
    
    // Method MainController uses to set the part to be modified
    public void setPart(Part part) {
        this.part = part;
        modifyPartIDField.setText(String.valueOf(part.getPartID()));
        modifyPartNameField.setText(part.getPartName());
        modifyPartInventoryField.setText(String.valueOf(part.getPartInventory()));
        modifyPartPriceField.setText(String.valueOf(part.getPartPrice()));
        modifyPartMaxField.setText(String.valueOf(part.getPartMax()));
        modifyPartMinField.setText(String.valueOf(part.getPartMin()));
        if(part instanceof Inhouse){
            modifyToggleTextField.setText(String.valueOf(((Inhouse) part).getPartMachineID()));
            modifyPartToggleLabel.setText("Machine ID");
            inHouseRadio.setSelected(true);
        }else{
            modifyToggleTextField.setText(((Outsourced) part).getCompanyName());
            modifyPartToggleLabel.setText("Company Name");
            outSourcedRadio.setSelected(true);
        }  
    }
    
    // Sets toggle label to Machine ID if In-House radio is selected
    // Sets toggle label to Company Name if Outsourced radio is selected
    @FXML
    void handleModifyPartRadio(ActionEvent event){
        try{
            if(inHouseRadio.isFocused()){
                modifyPartToggleLabel.setText("Machine ID");
            } else if(outSourcedRadio.isFocused()){
                modifyPartToggleLabel.setText("Company Name");
                
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
            Stage stage = (Stage) modifyPartCancelButton.getScene().getWindow();
            stage.close();
        } else{
        }
    }  
    
    /**
     * Action when the user clicks save part.
     * @param event 
     */
    @FXML
    void handleModifyPartSave(ActionEvent event){
        String partID = modifyPartIDField.getText();
        String partName = modifyPartNameField.getText();
        String partInventory = modifyPartInventoryField.getText();
        String partPrice = modifyPartPriceField.getText();
        String partMax = modifyPartMaxField.getText();
        String partMin = modifyPartMinField.getText();
        String toggle = modifyToggleTextField.getText();
        if(isInputValid() == true){
            if(inHouseRadio.isSelected() || outSourcedRadio.isSelected()){
                try{
                    if(inHouseRadio.isSelected()){
                        Inhouse inhouse = new Inhouse();

                        inhouse.setPartID(Integer.parseInt(partID));
                        inhouse.setPartName(partName);
                        inhouse.setPartInventory(Integer.parseInt(partInventory));
                        inhouse.setPartPrice(Double.parseDouble(partPrice));
                        inhouse.setPartMax(Integer.parseInt(partMax));
                        inhouse.setPartMin(Integer.parseInt(partMin));
                        inhouse.setPartMachineID(Integer.parseInt(toggle));
                
                        Inventory.updatePart(partIndex, inhouse);
                    } else if(outSourcedRadio.isSelected()){
                        Outsourced outsourced = new Outsourced();
                
                        outsourced.setPartID(Integer.parseInt(partID));
                        outsourced.setPartName(partName);
                        outsourced.setPartInventory(Integer.parseInt(partInventory));
                        outsourced.setPartPrice(Double.parseDouble(partPrice));
                        outsourced.setPartMax(Integer.parseInt(partMax));
                        outsourced.setPartMin(Integer.parseInt(partMin));
                        outsourced.setCompanyName(toggle);
                
                        Inventory.updatePart(partIndex, outsourced);
                    }
                    Stage stage = (Stage) modifyPartSaveButton.getScene().getWindow();
                    stage.close();
                } catch(Exception e){
                    e.printStackTrace();
                }
            } else if(inHouseRadio.isSelected() == false && outSourcedRadio.isSelected() == false){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error!");
                alert.setContentText("Select In-house or Outsourced!");

                alert.showAndWait();
            }
        }
    }
    
    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (modifyPartIDField.getText() == null || modifyPartIDField.getText().length() == 0) {
            errorMessage += "No valid part ID!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Integer.parseInt(modifyPartIDField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part ID (must be an integer)!\n"; 
            }
        }
        
        if (modifyPartNameField.getText() == null || modifyPartNameField.getText().length() == 0) {
                errorMessage += "No valid part name!\n"; 
        }
        
        if (modifyPartInventoryField.getText() == null || modifyPartInventoryField.getText().length() == 0) {
            errorMessage += "No valid part inventory!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Integer.parseInt(modifyPartInventoryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part inventory (must be an integer)!\n"; 
            }
        }
        
        if (modifyPartPriceField.getText() == null || modifyPartPriceField.getText().length() == 0) {
            errorMessage += "No valid part price!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Double.parseDouble(modifyPartPriceField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part price (must be a double)!\n"; 
            }
        }
        
        if (modifyPartMaxField.getText() == null || modifyPartMaxField.getText().length() == 0) {
            errorMessage += "No valid part max!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Integer.parseInt(modifyPartMaxField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part max (must be a integer)!\n"; 
            }
        }
        
        if (modifyPartMinField.getText() == null || modifyPartMinField.getText().length() == 0) {
            errorMessage += "No valid part min!\n"; 
        }else {
            // try to parse the part id into an int.
            try {
                Integer.parseInt(modifyPartMinField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid part min (must be a integer)!\n"; 
            }
        }
        
        if(outSourcedRadio.isSelected()){
            if (modifyPartNameField.getText() == null || modifyPartNameField.getText().length() == 0) {
            errorMessage += "No valid part company name!\n"; 
        }
        
        
        }
        if(inHouseRadio.isSelected()){
                try{
                    Integer.parseInt(modifyToggleTextField.getText());
                } catch(NumberFormatException e){
                    errorMessage += "No valid part machine ID (must be a integer)!\n";
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
      