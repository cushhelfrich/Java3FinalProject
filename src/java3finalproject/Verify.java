package java3finalproject;

import javafx.scene.control.Alert;

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
    public static boolean checkPriEmail(String n) {
        boolean flag = true;
        if (!n.matches("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+"
                + "(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("WARNING");
            alert.setHeaderText("Primary Email Format Error");
            alert.setContentText("Email should be in this format\n"
                    + "text@text.com\nPlease try again.");
            alert.showAndWait();
            flag = false;

        }
        return flag;
        
    } //End checkEmail method
    
} //End Subclass Verify