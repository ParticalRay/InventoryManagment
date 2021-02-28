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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import model.inventory;

/**
 * FXML Controller class
 *
 * @author Jonathon Makepeace
 */
public class AddProductMenuController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField invText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private Text errorDisplayTxt;

    @FXML
    private TextField partSearch;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> invLevelCol;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private TableView<Part> associatedPartTable;

    @FXML
    private TableColumn<Part, Integer> partAssoIdCol;

    @FXML
    private TableColumn<Part, String> partNameAssoCol;

    @FXML
    private TableColumn<Part, Integer> invLevelAssoCol;

    @FXML
    private TableColumn<Part, Double> priceAssoCol;

    /**
     * Temporary list of parts being used till the object is created to add to
     * associated list.
     */
    private ObservableList<Part> tempListOfParts;

    /**
     * Blank product being used to make changes to and return when saved.
     */
    Product blankProduct = new Product(4, "Love Potion", 6.99, 3, 1, 5);

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        partTableView.setItems(inventory.getObservablePartsList());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partAssoIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAssoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceAssoCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invLevelAssoCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }

    /**
     * Take in all given fields to create a product
     *  object based on whats given. 
     * @param event button clicked to save object
     * @throws IOException Exception clause in case something is 
     *  entered wrong or values do not match intended use. 
     */
    @FXML
    private void onActionSave(ActionEvent event) throws IOException, Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainMenu.fxml"));
        loader.load();
        MainMenuController MMCController = loader.getController();
        try {
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                String name = nameText.getText();
                if (name.matches("-?(0|[1-9]\\d*)")) { //To prevent an integer from being accepted in name
                    throw new Exception();
                }
                int stock = Integer.parseInt(invText.getText());
                double price = Double.parseDouble(priceText.getText());
                int min = Integer.parseInt(minText.getText());
                int max = Integer.parseInt(maxText.getText());

                if (min > max || min > stock || stock > max) {
                    Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                    warningAlert.setTitle("Warning Dialog");
                    warningAlert.setContentText("Min must be less than stock. Stock must be less than max.");
                    warningAlert.showAndWait();

                } else {
                    blankProduct.setId(MMCController.getID() + 4);
                    blankProduct.setName(name);
                    blankProduct.setStock(stock);
                    blankProduct.setPrice(price);
                    blankProduct.setMin(min);
                    blankProduct.setMax(max);

                    inventory.addProduct(blankProduct);
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid values for all fields");
            errorDisplayTxt.setText("Name must be a string\n"
                    + "Inventory must be an Integer\n"
                    + "Price must be a double\n"
                    + "Min must be an Integer\n"
                    + "Max must be an Integer");
            alert.showAndWait();
        }

    }

    /**
     * Cancel product creation and go back to main menu
     * @param event button clicked
     * @throws IOException Changing views
     */
    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * Add a new part object to the associated list
     * @param event button clicked
     */
    @FXML
    private void onActionAddPart(ActionEvent event) {
        Part p = partTableView.getSelectionModel().getSelectedItem();

        if (p != null) {

            blankProduct.addAssociatedPart(p);
            associatedPartTable.setItems(blankProduct.getAssociatedPartList());

        }

    }

    /**
     * Remove a part from the list
     * @param event button clicked
     */
    @FXML
    private void onActionRemovePart(ActionEvent event) {
        try{
            
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                
                blankProduct.deleteAssociatedPart(associatedPartTable.getSelectionModel().getSelectedItem());
            }
        }catch(NullPointerException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
        
    }

    /**
     * Search for a part object 
     * @param event text bar text present and entered
     */
    @FXML
    private void onActionSearch(ActionEvent event) {
        try {
            int id = Integer.parseInt(partSearch.getText());
            partTableView.getSelectionModel().select(selectPart(id));

        } catch (NumberFormatException e) {
            partTableView.setItems(filterPart(partSearch.getText()));
        } 
    }

    /**
     * Select a part based on its id
     *
     * @param id int id
     * @return the part of null if none found
     */
    public Part selectPart(int id) {
        for (Part p : inventory.getObservablePartsList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    /**
     * Filter method to search the list of parts and add to a filtered list to
     * be shown
     *
     * @param searchText search text given to look for parts
     * @return a list of parts with the given string
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
    

}
