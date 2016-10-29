package java3finalproject;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Cush Helfrich
 * @Assignment Name: java3finalproject
 * @Date: Oct 26, 2016
 * @Subclass Verify Description: Subclass used for verification of data entry
 */
//Imports

//Begin Subclass Verify
public class Verify {

    /**
     * checkEmail method: used to check syntax of email entered
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
     * isData Method:  Checks if field is empty
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
            alert.setContentText("You must enter a value int the " + l.getText() +
                    " field.  Please try again");
               
            alert.showAndWait();

            boolChecks = false;
        }

        return boolChecks;
        
    } //End isData method

    
} //End Subclass Verify