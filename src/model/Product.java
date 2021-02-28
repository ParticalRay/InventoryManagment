package model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Product extends inventory{
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor of product
     * @param id identification
     * @param name name of product
     * @param price price
     * @param stock amount in stock
     * @param min minimum amount
     * @param max maximum amount
     */
    public Product(int id, String name,
                   double price, int stock,
                   int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *  Set the id
     * @param id identification number
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the name
     * @param name name of product
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the price
     * @param price price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set the stock
     * @param stock amount in stock
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Minimum amount
     * @param min minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Maximum amount
     * @param max maximum
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Get the ID number
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * get the Name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get the price
     * @return price
     */
    public double getPrice() {
        return price;
    }

    /**
     * get the stock
     * @return stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * get the min
     * @return min
     */
    public int getMin() {
        return min;
    }

    /**
     * get the max
     * @return max
     */
    public int getMax() {
        return max;
    }

    /**
     * Add associated part to list
     * @param part part object being added
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    
    public void removeAssociatedPart(Part part) {
        associatedParts.remove(part);
    }

    /**
     * Delete the part from the associated list
     * @param selectedPart part being deleted
     * @return boolean value verifying if it was deleted
     */
    public boolean deleteAssociatedPart(Part selectedPart) {
        boolean found = false;
        int index = 0;
        while (!found && index < getAssociatedPartList().size()) {
            if (getAssociatedPartList().get(index) == selectedPart) {
                found = true;
                getAssociatedPartList().remove(selectedPart);
            } else {
                index++;
            }
        }
        return found;
    }

    /**
     * get the associated part list
     * @return the list
     */
    public ObservableList getAssociatedPartList() {
        return associatedParts;
    }


}
