package java3finalproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Cush Helfrich, Wayne Riley, Charlotte Hirschberger
 * @Assignment Name: java3finalproject
 * @Date: Oct 26, 2016
 * @Subclass Verify Description: Subclass used for verification of data entry
 */

//*******************Start Cush Code****************************
//Begin Subclass Verify
public class Verify {

    /**
     * checkEmail method: used to check syntax of email entered
     *
     * @param n
     * @return flag
     */
    public static boolean checkEmail(String n) {
        boolean flag = true;
        if (!n.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+"
                + "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Email Format Error");
            alert.setContentText("Email should be in this format\n"
                    + "text@text.com\nPlease try again.");
            alert.showAndWait();
            flag = false;

        }
        return flag;

    } //End checkEmail method

    /**
     * isData Method: Checks if field is empty
     *
     * @param t
     * @param l
     * @return boolChecks
     */
    public boolean isData(TextField t, Label l) {

        boolean boolChecks = true;
        if (t.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("No Data Error");
            alert.setContentText("You must enter a value in the " + l.getText()
                    + " field.  Please try again");

            alert.showAndWait();

            boolChecks = false;
        }

        return boolChecks;

    } //End isData method
    
    /**
     * isValidUsername method: Checks for valid username
     * @param username
     * @return boolChecks
     */
    public boolean isValidUsername (TextField username) {
        
        boolean boolChecks = true;
        
        if(!username.getText().matches("(?=[A-Za-z0-9-_.]{6,64}$)" //String has between 6-64 characters
                + "^[A-Za-z0-9]([-_.]{0,1}[A-Za-z0-9]+)+$")) {   //String starts and ends with alphanumeric data

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid Username");
            alert.setContentText("The username must be 6-64 characters in length, must\n"
                    + "start with a number or letter, and must contain only numbers,\n"
                    + "letters, dashes, underscores, and periods. ");
            alert.showAndWait();

            boolChecks = false;
        }
        
        return boolChecks;
        
    } //End isValidUsername method
    
    /**
     * isValidPassword method: Check for valid password
     * @param password
     * @return boolChecks
     */
    public boolean isValidPassword (PasswordField password) {
        
        boolean boolChecks = true;
        
        //Between 8-255, 1 digit, 1 capital, 1 symbol
         if(!password.getText().matches("(?=.*\\d)(?=.*[A-Z])(?=.*\\W).{8,255}")) { 


            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("ERROR");
            alert.setHeaderText("Invalid password");
            alert.setContentText("The password must be 8-255 characters in length and\n"
                    + "contain at least 1 digit, 1 symbol, and 1 uppercase letter.");

            alert.showAndWait();

            boolChecks = false;
        }
        
        return boolChecks;
        
    } //End isValidPassword

    //****************End Cush Code**********************************
    
    //****************Start Wayne Code**********************************
    /**
     * Alert if Account, username and password fields are blank
     */
    public void empty() {

        Alert blank = new Alert(Alert.AlertType.WARNING);
        blank.setTitle("Warning");
        blank.setHeaderText("Missing Information");
        blank.setContentText("You must populate Account, Username and Password fields");
        blank.showAndWait();
    }
    
       /**
     * Alert if username and password fields are blank for modify function
     */
    public void modifyEmpty() {

        Alert blank = new Alert(Alert.AlertType.WARNING);
        blank.setTitle("Warning");
        blank.setHeaderText("Missing Information");
        blank.setContentText("You must populate Username and/or Password field");
        blank.showAndWait();
    }

    /**
     * Alert if account only is blank
     */
    public void deleteEmpty() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Missing Information");
        missing.setContentText("You must populate Account field");
        missing.showAndWait();
    }

    /**
     * Alert if duplicate account exist
     */
    public void duplicate() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Duplicate Account");
        missing.setContentText("You must enter a unique Account Name");
        missing.showAndWait();
    }

    /**
     * Alert if duplicate account exist
     */
    public void noAct() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Account Does Not Exist");
        missing.setContentText("You must enter a valid account name");
        missing.showAndWait();
    }
    
     /**
     * Alert if duplicate account exist
     */
    public void sameUserNameEntry() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Same Username");
        missing.setContentText("Null out Username field or modify to make it different");
        missing.showAndWait();
    }
    
    
      /**
     * Alert if duplicate account exist
     */
    public void samePasswordEntry() {

        Alert missing = new Alert(Alert.AlertType.WARNING);
        missing.setTitle("Warning");
        missing.setHeaderText("Same Password");
        missing.setContentText("Null out Password field or modify to make it different");
        missing.showAndWait();
    }

    //****************End Wayne Code**********************************
    
    /***********Start Charlotte's Code******************/
    
    /**
     * Uses objects packaged in FieldSet to check user entries and set alert labels accordingly
     * @param fs        TextInputControl, regex, id string
     * @return          true if all entries are valid
     */
    public boolean isValidEntry (FieldSet fs)
    {
       boolean isValid = false;
       String entry = fs.getField().getText();
       
       if(entry.isEmpty())
       {
           fs.setAlert("! Enter a " + fs.getID());
       }
       else if(!entry.matches(fs.getRegex()))
       {
           fs.setAlert("! Invalid " + fs.getID());
       }
       else
       {
           isValid = true;
           fs.setAlert("");
       }
       
       return isValid;
    }
    
    /**
     * Accepts an AlertType and multiple strings, in order to efficiently create
     * an alert dialog
     * @param type      AlertType
     * @param header
     * @param content 
     */
    public void createAlert(Alert.AlertType type, String header, String content)
    {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
} //End Subclass Verify
