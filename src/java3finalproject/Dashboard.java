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
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    TextArea accountView;
    static TextField accountName = new TextField();
    static TextField userName = new TextField();
    static TextField passWord = new TextField();
    static TextField webSite = new TextField();

    //instantiate subclass
    Add add = new Add();

    /**
     * Main dashboard - User can view accounts or add, modify and delete
     * accounts
     */
    public void mainScreen() {

        //Borderpane to hold Gridpane, HBOX (holds buttons)
        BorderPane bp = new BorderPane();
        bp.setPadding(new Insets(10, 10, 10, 10));
        GridPane gPane = new GridPane();
        gPane.setAlignment(Pos.CENTER);
        gPane.setHgap(5);
        gPane.setVgap(5);

        gPane.add(accountName, 0, 0);
        GridPane.setConstraints(accountName, 0, 0, 1, 1);
        accountName.setPromptText("Enter Account Name Here");
        gPane.add(viewAccount, 0, 1);
        GridPane.setConstraints(viewAccount, 0, 1, 1, 1);
        gPane.add(userName, 0, 2);
        GridPane.setConstraints(userName, 0, 2, 1, 1);
        userName.setPromptText("Enter/View Username Here");
        gPane.add(passWord, 0, 3);
        GridPane.setConstraints(passWord, 0, 3, 1, 1);
        passWord.setPromptText("Enter/View Password Here");
        gPane.add(webSite, 0, 4);
        GridPane.setConstraints(webSite, 0, 4, 1, 1);
        webSite.setPromptText("Enter/View Website Here");

        /* Add panes to appropriate region */
        bp.setRight(gPane);
        bp.setBottom(gethBox());
        bp.setLeft(getvBox());

        //Dashboard stage
        Scene scene = new Scene(bp, 375, 200);
        dbScene.setTitle("Dashboard");
        dbScene.setScene(scene);
        dbScene.show();

    }

    private VBox getvBox() {
        /*Set vBox attributes */
        VBox vBox = new VBox(5);
        vBox.setAlignment(Pos.CENTER_LEFT);
        vBox.setPadding(new Insets(5, 5, 5, 5));

        accountView = new TextArea();
        accountView.setPrefColumnCount(15);
        accountView.setPrefRowCount(13);
        accountView.setEditable(false);
        accountView.setWrapText(true);
        accountView.appendText("Show Existing Accounts stored in Database Here");
        //ImageView image = new ImageView(new Image("images/smallLock.jpg"));

        vBox.getChildren().addAll(accountView);

        return vBox;
    }

    ;


    /**
     * get hBox and add buttons (Add, modify, delete, clear and Exit)
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
            add.insert(accountName.getText(), userName.getText(), passWord.getText(), webSite.getText());
        });//end Add event handler

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
        webSite.clear();
    }
} //End Subclass Dashboard
