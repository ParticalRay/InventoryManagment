
package Alpha;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.InHouse;
import model.Outsourced;
import model.Part;
import model.Product;
import model.inventory;

/**
 *
 * @author Jonathon Makepeace
 * 
 * Errors that I experienced in this project:
 *      1. Connection between InHouse, Outsourced and Part
 *      2. Connecting views together throughout project
 *      3. NullPointer Exception
 *      4. NumberFormat Exceptions
 * 
 * 1. Connecting InHouse, Outsourced was difficult until the 
 *  professor pointed out that I was missing super() from the classes.
 * 
 * 2. Connecting views was difficult to send and receive objects
 *  until I created a method to send and receive said objects. I learned
 *  this aspect from watching the professors webinars. 
 * 
 * 3. Null pointer error was from creating empty objects and not putting
 *  an object inside before altering it. Created an instance of each
 *  object to prevent null errors. 
 * 
 * 4. NumberFormat exception was from trying to get 
 *  certain values from a user and incorrect values
 *  being given. Solved by surrounding given values with try/catch
 *  and giving warnings.
 * 
 * Future feature to the project:
 * 
 * I would add a sell/buy option to quickly increase or decrease the
 *  amount in inventory to keep track of stock. After that feature would
 *  be a history list that would keep track of transactions. 
 *
 * 
 *
 */
public class JavaFXApplication extends Application{

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainMenu.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args){
        Product prod1 = new Product( 1, "toy", 5.60, 1, 1, 5);
        Product prod2 = new Product( 2, "chainsaw", 1.99, 2, 1, 5);
        Product prod3 = new Product( 3, "Cure for Cancer", 1.09, 4, 1, 5);
        Product prod4 = new Product( 4, "Love Potion", 6.99, 3, 1, 5);
        Part part1 = new InHouse(1,"Screw", 9.99, 6, 1, 20, 69);
        Part part2 = new Outsourced(2, "Foreign Screw", 10.00, 6,1,30,"Tesla");
        Part part3 = new InHouse(3,"Paper", 0.20,10,1,100,420);
        inventory.addPart(part1);
        inventory.addPart(part2);
        inventory.addPart(part3);
        inventory.addProduct(prod1);
        inventory.addProduct(prod2);
        inventory.addProduct(prod3);
        inventory.addProduct(prod4);
   
        
        
        launch(args);
    }
    

}
