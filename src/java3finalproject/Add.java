package java3finalproject;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Wayne Riley
 * @Assignment Name: java3finalproject
 * @Date: Oct 17, 2016
 * @Subclass Add Description: Content (Account, Username, password and website)
 * taken from dashboard textfields and inserted into database.
 */
//Imports
//Begin Subclass Add
//***********************Wayne***************************

public class Add {

    //declarations
    Stage addScene = new Stage();
    Button btnConfirm = new Button("Confirm");
    Button btnEdit = new Button("Edit");

    /**
     * Account name, user name, password and website received from dashboard textfields to this scene
     *
     * @param actName
     * @param usrName
     * @param pw
     * @param ws
     */
    public void insert(String actName, String usrName, String pw, String ws) {
       

        //declarations for Confirmation dialog
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        GridPane gp = new GridPane();

        Text header = new Text(15, 50, "Confirm New Account");
        header.setFont(Font.font("Times New Roman", FontWeight.EXTRA_BOLD, FontPosture.ITALIC, 15));
        pane.getChildren().addAll(header); //add children to top pane

        //labels        
        Label lblaccountName = new Label("Account Name");
        Label lblaccountGetText = new Label(actName);
        Label lblLUserName = new Label("User Name:");
        Label lbluserNameGetText = new Label(usrName);
        Label lblpassword = new Label("Password:");
        Label lblpasswordGetText = new Label(pw);
        Label lblwebsite = new Label("Website:");
        Label lblwebsiteGetText = new Label(ws);
        

        /* Add panes to appropriate region */
        bp.setTop(pane);
        bp.setCenter(gp);      

        /*Set GridPane attributes */
        gp.setAlignment(Pos.CENTER_LEFT);
        gp.setPadding(new Insets(5, 5, 5, 5));
        gp.setHgap(5);
        gp.setVgap(5);

        //add Account Name to grid pane
        gp.add(lblaccountName, 0, 0);
        GridPane.setConstraints(lblaccountName, 0, 0, 1, 1);
        gp.add(lblaccountGetText, 1, 0);
        GridPane.setConstraints(lblaccountGetText, 1, 0, 1, 1);
        //Add User Name to grid pane
        gp.add(lblLUserName, 0, 1);
        GridPane.setConstraints(lblLUserName, 0, 1, 1, 1);
        gp.add(lbluserNameGetText, 1, 1);
        GridPane.setConstraints(lbluserNameGetText, 1, 1, 1, 1);
        //add password to grid pane
        gp.add(lblpassword, 0, 2);
        GridPane.setConstraints(lblpassword, 0, 2, 1, 1);
        gp.add(lblpasswordGetText, 1, 2);
        GridPane.setConstraints(lblpasswordGetText, 1, 2, 1, 1);
        //add website to grid pane
        gp.add(lblwebsite, 0, 3);
        GridPane.setConstraints(lblwebsite, 0, 3, 1, 1);
        gp.add(lblwebsiteGetText, 1, 3);
        GridPane.setConstraints(lblwebsiteGetText, 1, 3, 1, 1);
        //add Buttons
        gp.add(btnConfirm, 0, 4);
        GridPane.setConstraints(btnConfirm, 0, 4, 1, 1);
        gp.add(btnEdit, 1, 4);
        GridPane.setConstraints(btnEdit, 1, 4, 1, 1);
        
        
        //lambda expression confirm and Edit
        btnConfirm.setOnAction((ActionEvent e) -> {
            addScene.close();
            //!!!!!!!!!!!!!!!!!business logic insertion here!!!!!!!!!!!!!!!
            System.out.println("Insert " + actName + " " + usrName + " " + pw + " " + ws + " into database");
            Dashboard.clearHandler();//calls Static method in main
        });//end confirm event handler

        //lambda expression Exit Program        
        btnEdit.setOnAction((ActionEvent e) -> {
            addScene.close();
        });//end edit event handler

        //New stage
        Scene scene = new Scene(bp, 250, 200);
        addScene.setTitle("Confirm Information");
        addScene.setScene(scene);
        addScene.show();
    }  

  
}//End Subclass Add
