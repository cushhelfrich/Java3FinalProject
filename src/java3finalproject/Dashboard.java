package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Wayne Riley
 * @Assignment Name: java3finalproject
 * @Date: Oct 16, 2016
 * @Subclass Dashboard Description: Dashboard called from Login screen once
 * proper UID/PID is accepted
 */
//Imports
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//Begin Subclass Dashboard
//***********************Wayne ***************************
public class Dashboard {

    //declarations
    Stage dbScene = new Stage();
    Button viewAccount = new Button("View Account");
    Button btnAdd;
    Button btnModify;
    Button btnDelete;
    Button btnClear;
    Button btnExit;
    static TextArea accountView;
    Alert blank;
    static TextField accountName = new TextField();
    static TextField userName = new TextField();
    static TextField passWord = new TextField();
    //static TextField webSite = new TextField();

    //instantiate subclass
    Add add = new Add();
    Delete delete = new Delete();
    DBConnector db = new DBConnector();

    /**
     * Main dashboard - User can view accounts or add, modify and delete
     * accounts
     */
    public void mainScreen() throws SQLException {

        //Borderpane to hold Gridpane, HBOX (holds buttons)
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));

        //Gridpane to hold Account, UserName and Password textfield as well as View Button
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(5);
        gridPane.setVgap(5);

        gridPane.add(accountName, 0, 0);
        GridPane.setConstraints(accountName, 0, 0, 1, 1);
        accountName.setPromptText("Enter Account Name");
        gridPane.add(viewAccount, 0, 1);
        GridPane.setConstraints(viewAccount, 0, 1, 1, 1);
        viewAccount.setMaxWidth(Double.MAX_VALUE);
        gridPane.add(userName, 0, 2);
        GridPane.setConstraints(userName, 0, 2, 1, 1);
        userName.setPromptText("Enter/View Username");
        gridPane.add(passWord, 0, 3);
        GridPane.setConstraints(passWord, 0, 3, 1, 1);
        passWord.setPromptText("Enter/View Password");
        //gridPane.add(webSite, 0, 4);
        //GridPane.setConstraints(webSite, 0, 4, 1, 1);
        //webSite.setPromptText("Enter/View Website Here");

        /* Add panes to appropriate region */
        bp.setRight(gridPane);
        bp.setBottom(gethBox());
        bp.setLeft(getvBox());

        //Add ID's to Nodes for CSS
        bp.setId("bp");
        gridPane.setId("root");
        btnAdd.setId("btn");
        btnModify.setId("btn");
        btnDelete.setId("btn");
        btnClear.setId("btn");
        btnExit.setId("btn");

        //Dashboard stage
        Scene scene = new Scene(bp, 370, 200);
        dbScene.setTitle("Dashboard");
        scene.getStylesheets().add(getClass().getClassLoader().getResource("login.css").toExternalForm());
        dbScene.setScene(scene);
        dbScene.show();

        account();
    }

    /**
     * VBox for textarea
     *
     * @return
     */
    private VBox getvBox() {
        /*Set vBox attributes */
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(5, 5, 5, 5));

        accountView = new TextArea();
        accountView.setPrefColumnCount(13);
        accountView.setPrefRowCount(8);
        accountView.setEditable(false);
        accountView.setWrapText(true);
        accountView.setText("ACCOUNT INFORMATION\n-----------------------------\n");
        //ImageView image = new ImageView(new Image("images/smallLock.jpg"));

        vBox.getChildren().addAll(accountView);

        return vBox;
    }

    ;

    /**
     * get hBox and buttons (Add, modify, delete, clear and Exit)
     *
     * @return
     */
    private HBox gethBox() {
        HBox hBox = new HBox(5);
        hBox.setAlignment(Pos.BASELINE_CENTER);
        hBox.setPadding(new Insets(5, 5, 5, 5));
        btnAdd = new Button("Add");
        btnModify = new Button("Modify");
        btnDelete = new Button("Delete");
        btnClear = new Button("Clear");
        btnExit = new Button("Exit");

        //Launches add confirmation scene
        btnAdd.setOnAction((ActionEvent e) -> {
            boolean blank = accountName.getText().matches("") || userName.getText().matches("")
                    || passWord.getText().matches("");
            if (blank == true) {
                empty();
            } else {
                add.insert(accountName.getText(), userName.getText(), passWord.getText());
            }
        });//end Add event handler

        //Clears TextFields
        btnDelete.setOnAction((ActionEvent e) -> {
            if (accountName.getText().matches("")) {
                deleteEmpty();
            } else {
                delete.delete(accountName.getText());
            }
        });//end clear event handler

        //Clears TextFields
        btnClear.setOnAction((ActionEvent e) -> {
            clearHandler();
        });//end clear event handler

        //Exit Program
        btnExit.setOnAction((ActionEvent e) -> {
            System.exit(0);
        });//end exit event handler

        hBox.getChildren().addAll(btnAdd, btnModify, btnDelete, btnClear, btnExit);

        return hBox;
    }

    /**
     * Method called to clear text fields
     */
    public static void clearHandler() {
        accountName.clear();
        userName.clear();
        passWord.clear();
        //webSite.clear();
    }

    /**
     * Calls dbConnector for connection calls dbConnector for query returns
     * account name Prints account names in Textarea.
     *
     * @throws SQLException
     */
    private void account() throws SQLException {

        db.makeConnection();
        //calls account to load accounts in textArea
        ResultSet rs = db.act();

        while (rs.next()) {
            accountView.appendText(rs.getString(1) + "\n");//append text area;
        }
    }

    /**
     * Alert if fields are blank
     */
    private void empty() {

        blank = new Alert(Alert.AlertType.WARNING);
        blank.setTitle("Warning");
        blank.setHeaderText("Missing Information");
        blank.setContentText("Please Populate Account, Username and Password Fields");
        blank.showAndWait();
    }

    /**
     * Alert if account is blank
     */
    private void deleteEmpty() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Missing Information");
        missing.setContentText("Please Populate Account Field");
        missing.showAndWait();
    }

} //End Subclass Dashboard
