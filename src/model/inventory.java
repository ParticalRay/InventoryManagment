package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//Top of hierarchy
public class inventory {

    private static ObservableList<Part> ObservablePartsList = FXCollections.observableArrayList();
    private static ObservableList<Product> ObservableProdList = FXCollections.observableArrayList();

    private static ObservableList<Part> filterParts = FXCollections.observableArrayList();
    private static ObservableList<Product> filterProducts = FXCollections.observableArrayList();

    /**
     * Getter for the list of filtered parts
     * @return list of filtered parts
     */
    public static ObservableList<Part> getFilterParts() {
        return filterParts;
    }

    /**
     * Getter for the list of filtered products
     * @return list of filtered products
     */
    public static ObservableList<Product> getFilterProducts() {
        return filterProducts;
    }

    /**
     * Add a part to the current list of parts
     *
     * @param newPart part object
     */
    public static void addPart(Part newPart) {
        getObservablePartsList().add(newPart);
    }

    /**
     * add a product to the current list of products
     *
     * @param addProduct product object
     */
    public static void addProduct(Product addProduct) {
        getObservableProdList().add(addProduct);
    }

    /**
     * Look up the part based on its id
     * @param partId given part id
     * @return found part 
     */
    public Part lookupPart(int partId) {
        Part foundPart = getObservablePartsList().get(partId);

        return foundPart;//return the part requested
    }

    /**
     * Look up the product based on its id
     * @param productId given product id
     * @return found product
     */
    public Product lookupProd(int productId) {
        Product foundProd = ObservableProdList.get(productId);
        return foundProd;
    }

   /**
    * Getter for the list of parts
    * @return list of parts
    */
    public static ObservableList<Part> getObservablePartsList() {
        return ObservablePartsList;
    }

    /**
     * Getter for the list of products
     * @return list of products
     */
    public static ObservableList<Product> getObservableProdList() {
        return ObservableProdList;
    }


}
