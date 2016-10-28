package java3finalproject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    Label lblUserName = new Label("Username");
    Label lblEmail = new Label("Email:");
    Label lblFirstName = new Label("First Name:");
    Label lblLastName = new Label("Last Name:");
    Label lblPassword = new Label("Password:");
    
    
    //TextArea
    TextField tfUserName = new TextField();
    TextField tfEmail = new TextField();
    TextField tfFirstName = new TextField();
    TextField tfLastName = new TextField();
    
    PasswordField password = new PasswordField();//Password
    
    Button btnConfirmUser = new Button ("Confirm");
    Button btnEditUser = new Button ("Edit");
   
    
    public void createUser() {
        
        bpCreateUser.setTop(vbText());//Add vbTop to bpMain top by calling method
        bpCreateUser.setCenter(gpCenter());//Add GridPane to center
        bpCreateUser.setBottom(hbButtons());
        
        //Create Scene and add mainBorder to it
        Scene myScene = new Scene(bpCreateUser, 400, 300);

        //Setup Stage
        createUserStage.setTitle("Create User"); //Set the title of the Stage
        myScene.getStylesheets().add(getClass().getClassLoader().getResource("createuser.css").toExternalForm());
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
    public GridPane gpCenter() {
        
        //Used to see lines for GridPane rows/colums for troubleshooting
        //gpSorts.setGridLinesVisible(true);
        GridPane gpEntries = new GridPane(); //Create GridPane instance

        //Set the gaps
        gpEntries.setHgap(5);
        gpEntries.setVgap(5);

        gpEntries.setPadding(new Insets(5, 5, 5, 5));
        gpEntries.setStyle("-fx-border-color:red");

        /**
         * Note the following when adding to GridPane
         * GridPane.setConstraints(node, columnIndex, rowIndex, columnspan,
         * rowspan); gpSorts.add(node, columnIndex, rowIndex);
         *
         * Add Label and TextField for name and age to top of
         * bpCenterLeft(BorderPane)
         */
        gpEntries.add(lblUserName, 0, 0);
        GridPane.setConstraints(lblUserName, 0, 0, 1, 1);

        gpEntries.add(tfUserName, 1, 0);
        GridPane.setConstraints(tfUserName, 1, 0, 1, 1);

        gpEntries.add(lblEmail, 0, 1);
        GridPane.setConstraints(lblEmail, 0, 1, 1, 1);

        gpEntries.add(tfEmail, 1, 1);
        GridPane.setConstraints(tfEmail, 1, 1, 1, 1);

        gpEntries.add(lblFirstName, 0, 2);
        GridPane.setConstraints(lblFirstName, 0, 2, 1, 1);

        gpEntries.add(tfFirstName, 1, 2);
        GridPane.setConstraints(tfFirstName, 1, 2, 1, 1);

        gpEntries.add(lblLastName, 0, 3);
        GridPane.setConstraints(lblLastName, 0, 3, 1, 1);

        gpEntries.add(tfLastName, 1, 3);
        GridPane.setConstraints(tfLastName, 1, 3, 1, 1);

        gpEntries.add(lblPassword, 0, 4);
        GridPane.setConstraints(lblPassword, 0, 4, 1, 1);

        gpEntries.add(password, 1, 4);
        GridPane.setConstraints(password, 1, 4, 1, 1);

        

        //Set last label to good width for all labels in GridPane (0, #)
        lblPassword.setPrefWidth(75);

        return gpEntries;
        
    }
    
    /**
     * hbButtons Method: this will create a HBox to hold the buttons at the
     * bottom of the bpMain
     *
     * @return hbBottom
     */
    public HBox hbButtons() {

        HBox hbBottom = new HBox(10);  //HBox for buttons at the bottom

        hbBottom.setAlignment(Pos.CENTER); //Set Buttoms to center
        hbBottom.setPadding(new Insets(5, 1, 1, 1)); //Padd it

        //Add buttons to the bottom HBox
        hbBottom.getChildren().addAll(btnConfirmUser,btnEditUser);

        //Handlers
        btnConfirmUser.setOnAction(new EnterHandler()); //Handler for confirm
        
       

        return hbBottom;

    } //End hbButtons

    class EnterHandler implements EventHandler<ActionEvent> {
        
       @Override
        public void handle(ActionEvent e) {
            
            boolean checks = true;
            if (checks != false) {
                
                if (!Verify.checkEmail(tfEmail.getText())) {
                    tfEmail.clear();
                    tfEmail.requestFocus();
                    
                }
                    
            }
            if (checks) {
                confirmStage();
            }
            
        }
    }
    
    public void confirmStage(){
        
        Stage confirmStage = new Stage();
        BorderPane confirmBorder = new BorderPane();
       
        
    }
    
} //End Subclass CreateUser