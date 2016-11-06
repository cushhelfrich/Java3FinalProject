package java3finalproject;

/** 
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Riley Laptop
 * @Assignment Name: java3finalproject
 * @Date: Oct 29, 2016
 * @Subclass Modify Description: 
 */
//Imports
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//Begin Subclass Modify
class Modify {
    
//declarations
    Stage modifyScene = new Stage();
    Button btnConfirm = new Button("Confirm");
    Button btnEdit = new Button("Edit");

    /**
     * Account name, user name, and password received from dashboard textfields
     * to this scene
     *
     * @param actName
     * @param usrName
     * @param pw
     * @param ws
     */
    public void change(String actName, String usrName, String pw) {

        //declarations for Confirmation dialog
        Pane pane = new Pane();
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gridPane = new GridPane();

        //DropShadow effect 
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(5);
        dropShadow.setOffsetY(5);

        Text header = new Text(35, 15, "Modify Account\n Are you sure?");
        header.setFont(Font.font("Courier New", FontWeight.BOLD, 20));
        pane.getChildren().addAll(header); //add children to top pane
        header.setEffect(dropShadow);

        //labels        
        Label lblaccountName = new Label("Account Name:");
        Label lblaccountGetText = new Label(actName);
        Label lblLUserName = new Label("User Name:");
        Label lbluserNameGetText = new Label(usrName);
        Label lblpassword = new Label("Password:");
        Label lblpasswordGetText = new Label(pw);
        //Label lblwebsite = new Label("Website:");
        //Label lblwebsiteGetText = new Label(ws);        

        /* Add panes to appropriate region */
        bp.setTop(pane);
        bp.setCenter(gridPane);

        //Add ID's to Nodes for CSS
        bp.setId("bp");
        gridPane.setId("root");
        btnConfirm.setId("btn");
        btnEdit.setId("btn");
        header.setId("text");

        /*Set GridPane attributes */
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        //add Account Name to grid pane
        //gridPane.add(lblaccountName, 0, 0);
        //GridPane.setConstraints(lblaccountName, 0, 0, 1, 1);
        //gridPane.add(lblaccountGetText, 1, 0);
        //GridPane.setConstraints(lblaccountGetText, 1, 0, 1, 1);
        //Add User Name to grid pane
        //gridPane.add(lblLUserName, 0, 1);
        //GridPane.setConstraints(lblLUserName, 0, 1, 1, 1);
        //gridPane.add(lbluserNameGetText, 1, 1);
        //GridPane.setConstraints(lbluserNameGetText, 1, 1, 1, 1);
        //add password to grid pane
        //gridPane.add(lblpassword, 0, 2);
        //GridPane.setConstraints(lblpassword, 0, 2, 1, 1);
        //gridPane.add(lblpasswordGetText, 1, 2);
        //GridPane.setConstraints(lblpasswordGetText, 1, 2, 1, 1);
        //add website to grid pane
        //gridPane.add(lblwebsite, 0, 3);
        //GridPane.setConstraints(lblwebsite, 0, 3, 1, 1);
        //gridPane.add(lblwebsiteGetText, 1, 3);
        //GridPane.setConstraints(lblwebsiteGetText, 1, 3, 1, 1);
        //add Buttons
        gridPane.add(btnConfirm, 0, 4);
        GridPane.setConstraints(btnConfirm, 0, 4, 1, 1);
        gridPane.add(btnEdit, 1, 4);
        GridPane.setConstraints(btnEdit, 1, 4, 1, 1);

        //lambda expression confirm and Edit
        btnConfirm.setOnAction((ActionEvent e) -> {
            modifyScene.close();
            //!!!!!!!!!!!!!!!!!business logic or call class to perform function!!!!!!!!!!!!!!!
            
            System.out.println("Modify " + actName + " " + usrName + " " + pw + " " + " in database");
            Dashboard.clearHandler();//calls Static method in main
        });//end confirm event handler

        //lambda expression Exit Program        
        btnEdit.setOnAction((ActionEvent e) -> {
            modifyScene.close();
        });//end edit event handler

        //New stage
        Scene scene = new Scene(bp, 275, 180);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        modifyScene.setTitle("Modify Information");
        modifyScene.setScene(scene);
        modifyScene.show();
    }      
    

} //End Subclass Modify