/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.InHouse;
import model.Part;
import model.Product;
import model.inventory;

/**
 * FXML Controller class
 *
 * @author Jonathon Makepeace
 */
public class MainMenuController implements Initializable {

    Stage stage;
    Parent scene;
        
      @FXML
    private TableColumn<Part, Integer> partIDCol;
      
    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInvCol;

    @FXML
    private TableColumn<Part, Double> partCostCol;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> productInvCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TextField partSearchBar;

    @FXML
    private TextField productSearchBar;


    @FXML
    private Button exitButton;
    @FXML
    private TableView<Part> partTableView;
    @FXML
    private TableView<Product> prodTableView;
    
    private static int instanceID;
    /**
     * Get the integer id
     * @return id
     */
    public int getID(){
        instanceID++;
        return instanceID;
    }

    

    /**
     * Select product by using its id
     * @param id int id
     * @return the product or null
     */
    public Product selectProduct(int id){
        for(Product p : inventory.getObservableProdList()){
            if(p.getId() == id)
                return p;
        }
        return null;
    }
    
    /**
     * Select part based on its id
     * @param id int id
     * @return part or null
     */
    public Part selectPart(int id){
        for(Part p : inventory.getObservablePartsList()){
            if(p.getId() == id)
                return p;
        }
        return null;
    }
    
    /**
     * Deletes part based on its id
     * @param id int id
     * @return remove part or return false
     */
    public boolean deletePart(int id){
        for(Part p : inventory.getObservablePartsList()){
            if(p.getId() == id){
                return inventory.getObservablePartsList().remove(p);
            }
        }
        return false;
    }
    
    /**
     * Deletes product based on its id
     * @param id int id
     * @return remove product or return false
     */
    public boolean deleteProd(int id){
        for(Product p : inventory.getObservableProdList()){
            if(p.getId() == id){
                return inventory.getObservableProdList().remove(p);
            }
        }
        return false;
    }
    
    /**
     * Search the list for the product based on the string 
     *  of text given to match its name
     * @param searchText string of text
     * @return list of products
     */
    public ObservableList<Product> filterProd(String searchText){
        if(!(inventory.getFilterProducts()).isEmpty())
            inventory.getFilterProducts().clear();
        for(Product p : inventory.getObservableProdList()){
            if(p.getName().contains(searchText))
                inventory.getFilterProducts().add(p);
        }
        if(inventory.getFilterProducts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not find a product with the given information");
            alert.showAndWait();
            return inventory.getObservableProdList();
        }else{
            return inventory.getFilterProducts();
        }
    }
    
    /**
     * Search the list for the part based on the string 
     *  of text given to match its name
     * @param searchText string of text
     * @return list of part
     */
    public ObservableList<Part> filterPart(String searchText){
        if(!(inventory.getFilterParts()).isEmpty()){
            inventory.getFilterParts().clear();
        }
        for(Part p : inventory.getObservablePartsList()){
            
            if(p.getName().contains(searchText)){
                inventory.getFilterParts().add(p);
            }
        }
        if(inventory.getFilterParts().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not find a part with the given information");
            alert.showAndWait();
            return inventory.getObservablePartsList();
        }else{
            return inventory.getFilterParts();
        }
    }
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        prodTableView.setItems(inventory.getObservableProdList());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        
        partTableView.setItems(inventory.getObservablePartsList());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
       
            
               
    }    

    /**
     * Switch to add part menu to create a part object
     * @param event button clicked
     * @throws IOException change in views
     */
    @FXML
    private void onActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/AddPartMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Modify a current part object. 
     * Must click on a part or error will show
     * @param event button clicked 
     * @throws IOException change in views
     */
    @FXML
    private void onActionModifyPart(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ModifyPartMenu.fxml"));
            loader.load();

            ModifyPartMenuController MPMController = loader.getController();

            MPMController.setInPart(partTableView.getSelectionModel().getSelectedItem());
            inventory.getObservablePartsList().remove(partTableView.getSelectionModel().getSelectedItem());

            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please select a part to modify");
                alert.showAndWait();
            }
        
    }

    /**
     * Deleted the selected part object
     * @param event button clicked
     */
    @FXML
    private void onActionDeletePart(ActionEvent event) {
        try{
            
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                
                deletePart(partTableView.getSelectionModel().getSelectedItem().getId());
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }

    /**
     * Switch to add product menu to create a product object
     * @param event button clicked
     * @throws IOException change in views
     */
    @FXML
    private void onActionAddProduct(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/AddProductMenu.fxml"));
        loader.load();
        
        AddProductMenuController APMController = loader.getController();
        
        
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
        
        
              
    }

    /**
     * Modify a current product object
     * @param event button clicked
     * @throws IOException change in views
     */
    @FXML
    private void onActionModifyProduct(ActionEvent event) throws IOException {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/ModifyProductMenu.fxml"));
            loader.load();

            ModifyProductMenuController MPMController = loader.getController();
            MPMController.setProduct(prodTableView.getSelectionModel().getSelectedItem());
            inventory.getObservableProdList().remove(prodTableView.getSelectionModel().getSelectedItem());


            stage = (Stage)((Button)event.getSource()).getScene().getWindow(); 
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to modify");
            alert.showAndWait();
        }
        
    }

    /**
     * Deletes a product if it is selected and must
     *  not have any parts associated to it
     * @param event button clicked
     */
    @FXML
    private void onActionDeleteProduct(ActionEvent event) {
        try{
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Product p = prodTableView.getSelectionModel().getSelectedItem();
                if(p.getAssociatedPartList().isEmpty()){
                    deleteProd(p.getId());
                }else{
                    Alert listAlert = new Alert(Alert.AlertType.WARNING);
                    listAlert.setContentText("Cannot erase a product with a list of associated parts");
                    listAlert.showAndWait();
                }
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a product to delete");
            alert.showAndWait();
        }
    }

    /**
     * Exit the program
     * @param event button clicked
     */
    @FXML
    private void onActionExit(ActionEvent event) {
        System.exit(0);
    }    

    /**
     * Search for a part based on its id or name
     * @param event click search bar and click enter
     */
    @FXML
    private void onActionPartSearch(ActionEvent event) {
        try{
            int id = Integer.parseInt(partSearchBar.getText());
            partTableView.getSelectionModel().select(selectPart(id));
         
        } catch(NumberFormatException e){
            partTableView.setItems(filterPart(partSearchBar.getText()));
        }
    }

    /**
     * Search for a product based on its id or name
     * @param event click search bar and click enter
     */
    @FXML
    private void onActionProductSearch(ActionEvent event) {
        
        try{
            int id = Integer.parseInt(productSearchBar.getText());
            prodTableView.getSelectionModel().select(selectProduct(id));
         
        } catch(NumberFormatException e){
            prodTableView.setItems(filterProd(productSearchBar.getText()));
        }
    }
}
