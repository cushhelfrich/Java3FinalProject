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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//************Start Wayne Code*********************
//Begin Subclass Dashboard
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
    private static List<Map<String, Object>> account;
    public static List<Account> accountArr = new ArrayList<>();
    static TextField accountName = new TextField();
    static TextField userName = new TextField();
    static TextField passWord = new TextField();
    //static TextField webSite = new TextField();

    //instantiate subclass
    Add add = new Add();
    Delete delete = new Delete();
    Modify modify = new Modify();
    Verify verify = new Verify();

    /**
     * Main dashboard - User can view accounts or add, modify and delete
     * accounts
     *
     * @throws java.sql.SQLException
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

        getAct();//calls account method to load ArrayList from database
        initAccountSet();
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
        //accountView.setText("ACCOUNT INFORMATION\n-----------------------------\n");
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
            if (isDuplicate(accountName.getText())) {
                verify.duplicate();
            } else if (blank == true) {
                verify.empty();
            } else {
                add.insert(accountName.getText(), userName.getText(), passWord.getText());
            }
        }
        );//end Add event handler
        viewAccount.setOnAction(
                (ActionEvent e) -> {
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } else {
                        Account dispAccount = new Account(accountName.getText());
                        userName.setText(dispAccount.getUserName());
                        passWord.setText(dispAccount.getPassword());
                        passWord.setText(AEScrypt.decrypt(dispAccount.getPassword(), "DonaTellaNobody"));

                       //modify.change(accountName.getText(), userName.getText(), passWord.getText());
                    }
                }
        );
        //Launches modify confirmation scene
        btnModify.setOnAction(
                (ActionEvent e) -> {
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } else {
                        modify.change(accountName.getText(), userName.getText(), passWord.getText());
                    }
                }
        );//end modify event handler

        //Launches delete confirmation scene
        btnDelete.setOnAction(
                (ActionEvent e) -> {
                    if (accountName.getText().matches("")) {
                        verify.deleteEmpty();
                    } else {
                        delete.delete(accountName.getText());
                    }
                }
        );//end delete event handler

        //Clears TextFields
        btnClear.setOnAction(
                (ActionEvent e) -> {
                    clearHandler();
                }
        );//end clear event handler

        //Exit Program
        btnExit.setOnAction(
                (ActionEvent e) -> {
                    System.exit(0);
                }
        );//end exit event handler

        hBox.getChildren()
                .addAll(btnAdd, btnModify, btnDelete, btnClear, btnExit);

        return hBox;
    }

    /**
     * Calls dbConnector for connection and query returns account name which
     * loads to ArrayList
     *
     * @throws SQLException
     */
    private void getAct() throws SQLException {
        if(account != null && !account.isEmpty())
        {
            account.clear(); // clears arrayList
        }
        String rtrvAct = "SELECT account_name FROM account WHERE user_id = " + Login.currUser.getUserId();

        //calls account to load accounts in arraylist
        account = Login.db.retrieveRecords(rtrvAct);
    }

    /**
     * Read ArrayList and display in text area.
     */
    private static void initAccountSet() {
        accountView.setText("ACCOUNT NAME\n-----------------------------\n");
        Map<String, Object> row;
        if(!account.isEmpty())
        {
            for (int i = 0; i < account.size(); i++) 
            {
                row = account.get(i);
                Account acct = new Account(
                    (String)row.get("account_name"), 
                    (String)row.get("username"), 
                    (String)row.get("password"),
                    (Timestamp)row.get("created"),
                    (Timestamp)row.get("last_update")
                );
                accountArr.add(acct);
                accountView.appendText(acct.getName() + "\n");
            }
        }
    }
    
    public static void updateAccountSet(Account newAcct) {
        accountView.appendText(newAcct.getName() + "\n");
        accountArr.add(newAcct);
    }
    
    public static void deleteAccount(String acctName)
    {
        boolean contSearch = true;
        int i = 0;
        while(contSearch == true)
        {
            if(accountArr.get(i).getName().equals(acctName))
            {
                accountArr.remove(i);
                contSearch = false;
            }
            i++;
        }
        
        accountView.setText("ACCOUNT NAME\n-----------------------------\n");
        for (Account acct : accountArr) {
            accountView.appendText(acct.getName() + "\n");
        }
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
    
    public static boolean isDuplicate(String acctName)
    {
        boolean b = false;
        for(int i = 0; i < accountArr.size(); i++)
        {
            if(accountArr.get(i).getName().equals(acctName))
            {
                b = true;
            }
        }
        
        return b;
    }
} //End Subclass Dashboard
//*********************End Wayne Code******************************
