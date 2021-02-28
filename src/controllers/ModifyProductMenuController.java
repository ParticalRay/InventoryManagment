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
import javafx.collections.FXCollections;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Part;
import model.Product;
import model.inventory;

/**
 * FXML Controller class
 *
 * @author jonat
 */
public class ModifyProductMenuController implements Initializable {

    private static ObservableList<Part> allPartList = FXCollections.observableArrayList();
    private static ObservableList<Part> associatedPartList = FXCollections.observableArrayList();

    Stage stage;
    Parent scene;

    @FXML
    private TextField idText;

    @FXML
    private TextField nameText;

    @FXML
    private TextField inventoryText;

    @FXML
    private TextField priceText;

    @FXML
    private TextField maxText;

    @FXML
    private TextField minText;

    @FXML
    private Text errorText;

    @FXML
    private TextField partSearchText;

    @FXML
    private TableView<Part> allPartTableView;

    @FXML
    private TableView<Part> associatedPartView;

    @FXML
    private TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> inventoryCol;

    @FXML
    private TableColumn<Part, Double> priceCol;

    @FXML
    private TableColumn<Part, Integer> partIDAssoCol;

    @FXML
    private TableColumn<Part, String> partNameAssoCol;

    @FXML
    private TableColumn<Part, Integer> inventoryAssoCol;

    @FXML
    private TableColumn<Part, Double> priceAssoCol;

    public ObservableList<Part> filterPart(String searchText) {
        if (!(inventory.getFilterParts()).isEmpty()) {
            inventory.getFilterParts().clear();
        }
        for (Part p : inventory.getObservablePartsList()) {

            if (p.getName().contains(searchText)) {
                inventory.getFilterParts().add(p);
            }
        }
        if (inventory.getFilterParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Could not find a part with the given information");
            alert.showAndWait();
            return inventory.getObservablePartsList();
        } else {
            return inventory.getFilterParts();
        }
    }

    /**
     * Get the current product from the previous screen then load the values in
     * the bars to be modified
     *
     * @param p product
     */
    public void setProduct(Product p) {
        idText.setText(String.valueOf(p.getId()));
        nameText.setText(String.valueOf(p.getName()));
        inventoryText.setText(String.valueOf(p.getStock()));
        priceText.setText(String.valueOf(p.getPrice()));
        maxText.setText(String.valueOf(p.getMax()));
        minText.setText(String.valueOf(p.getMin()));

        associatedPartView.setItems(p.getAssociatedPartList());
        partIDAssoCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAssoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceAssoCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryAssoCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        originalProduct = p;
    }

    private Product originalProduct;

    /**
     * Get the saved original product in case user clicks cancel or exits
     *
     * @return original product
     */
    public Product getSavedProduct() {
        return originalProduct;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        allPartTableView.setItems(inventory.getObservablePartsList());
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

        partIDAssoCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAssoCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceAssoCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryAssoCol.setCellValueFactory(new PropertyValueFactory<>("stock"));

    }

    @FXML
    private void onActionSave(ActionEvent event) throws IOException, Exception {
        try {
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {

                int id = Integer.parseInt(idText.getText());
                String name = nameText.getText();
                if (name.matches("-?(0|[1-9]\\d*)")) { //To prevent an integer from being accepted in name
                    throw new Exception();
                }
                int stock = Integer.parseInt(inventoryText.getText());
                double price = Double.parseDouble(priceText.getText());
                int min = Integer.parseInt(minText.getText());
                int max = Integer.parseInt(maxText.getText());

                if (min > max || min > stock || stock > max) {
                    Alert warningAlert = new Alert(Alert.AlertType.WARNING);
                    warningAlert.setTitle("Warning Dialog");
                    warningAlert.setContentText("Min must be less than stock. Stock must be less than max.");
                    warningAlert.showAndWait();

                } else {
                    getSavedProduct().setId(id);
                    getSavedProduct().setName(name);
                    getSavedProduct().setStock(stock);
                    getSavedProduct().setPrice(price);
                    getSavedProduct().setMin(min);
                    getSavedProduct().setMax(max);

                    inventory.addProduct(getSavedProduct());
                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please enter valid values for all fields");
            errorText.setText("Name must be a string\n"
                    + "Inventory must be an Integer\n"
                    + "Price must be a double\n"
                    + "Min must be an Integer\n"
                    + "Max must be an Integer");
            alert.showAndWait();
        }

    }

    @FXML
    private void onActionCancel(ActionEvent event) throws IOException {
        inventory.addProduct(getSavedProduct());
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    private void onActionAddPart(ActionEvent event) {
        getSavedProduct().addAssociatedPart(allPartTableView.getSelectionModel().getSelectedItem());

    }

    @FXML
    private void onActionRemovePart(ActionEvent event) {
        try{
            Alert warning = new Alert(Alert.AlertType.CONFIRMATION);
            Optional<ButtonType> result = warning.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                getSavedProduct().removeAssociatedPart(associatedPartView.getSelectionModel().getSelectedItem());
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select a part to delete");
            alert.showAndWait();
        }
    }

    @FXML
    private void onActionSearchPart(ActionEvent event) {
        try {
            int id = Integer.parseInt(partSearchText.getText());
            allPartTableView.getSelectionModel().select(selectPart(id));

        } catch (NumberFormatException e) {
            allPartTableView.setItems(filterPart(partSearchText.getText()));
        }
    }

    public Part selectPart(int id) {
        for (Part p : inventory.getObservablePartsList()) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

}
