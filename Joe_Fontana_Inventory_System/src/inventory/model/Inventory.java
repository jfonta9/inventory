
package inventory.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Inventory {
   
    private static int currentPartID;
    private static int currentProductID;
    private static ObservableList<Product> products = FXCollections.observableArrayList();
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    
    // Methods to return part ID and product ID
    
    public static int getCurrentPartID(){
        currentPartID++;
        return currentPartID;
    }
    public static int getCurrentProductID(){
        currentProductID++;
        return currentProductID;
    }
    
    // Methods to return Part and Product ObservableLists
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }
    public static ObservableList<Product> getAllProducts(){
        return products;
    }
    
    // Method to add a part object to the array list allParts
    // Method to add a product object to the array list products
    public static void addPart(Part part){
        allParts.add(part);
    }
    public static void addProduct(Product Product){
        products.add(Product);
    }
    
    // Method to delete a part object by name from the array list allParts
    // Method to delete a product object by name from the array list products
    public static boolean deletePart(Part removePart){
        allParts.remove(removePart);
        return true;
    }
    public static boolean deleteProduct(Product removeProduct){
        products.remove(removeProduct);
        return true;
    }
    
    // Method to update a part 
    // Method to update a product 
    public static void updatePart(int index, Part part){
        allParts.set(index, part);
    }
    public static void updateProduct(int index, Product product){
        products.set(index, product);
    }
    
    // Method to lookup a part by id number
    // Method to lookup a product by id number
    // Both located in Controllers
}
    
    
