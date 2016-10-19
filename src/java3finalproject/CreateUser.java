package java3finalproject;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Cush Helfrich
 * @Assignment Name: java3finalproject
 * @Date: Oct 18, 2016
 * @Subclass CreateUser Description: 
 */
//Imports

//Begin Subclass CreateUser
public class CreateUser {
    
    Stage createUserStage = new Stage();
    //The main BorderPane
    BorderPane bpCreateUser = new BorderPane();
    
    //Labels to show user
    Label lblEmail = new Label("Email:");
    Label lblFirstName = new Label("First Name:");
    Label lblLastName = new Label("Last Name:");
    Label lblPassword = new Label("Password:");
    
    
    //TextArea
    TextArea taEmail = new TextArea();
    TextArea taFirstName = new TextArea();
    TextArea taLastName = new TextArea();
    
    PasswordField taPassword = new PasswordField();//Password
   
    
    public void createUser() {
        
        bpCreateUser.setTop(vbText());//Add vbTop to bpMain top by calling method
        
        //Create Scene and add mainBorder to it
        Scene myScene = new Scene(bpCreateUser, 400, 300);

        //Setup Stage
        createUserStage.setTitle("Create User"); //Set the title of the Stage
        createUserStage.setScene(myScene);  //Add Scene to Stage
        
        createUserStage.show(); //Show Stage
    }
    
    public VBox vbText() {

        VBox vbTop = new VBox(); //VBox for text at the top

        vbTop.setAlignment(Pos.CENTER); //Align to center

        //Create the text for the BorderPane top
        Text mainText1 = new Text("User creation");
        Text mainText2 = new Text("Please fill out the following fields");

        //Set attributes for the text
        mainText1.setFont(Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 25));
        mainText2.setFont(Font.font("Times New Roman", FontWeight.BOLD,
                FontPosture.ITALIC, 15));

        vbTop.getChildren().addAll(mainText1, mainText2); //Add text to VBox

        return vbTop;

    } //End vbText method

    
} //End Subclass CreateUser