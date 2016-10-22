package java3finalproject;

/** 
 * @Course: SDEV 250 ~ Java Programming III
 * @Author Name: Bill Tanona
 * @Assignment Name: java3finalproject
 * @Date: Oct 20, 2016
 * @Subclass Account Description: 
 * 
 * The Account class will represent a single acount with:
 * 
 * private members
 *    username and  password both encrypted
 *    account type 
 *    
 * 
 * 
 */
//Imports

//Begin Subclass Account
public class Account {
    private final String accountName;
    private final String username;
    private final String password;
    private final String website;
    
    
    // constructor 
    
    public Account(String name, String uname, String password){
        this.accountName=name;
        this.username=uname;
        this.password=password;
        this.website="na";
    }
   
    public String getName(){
        return accountName;
    }
    public String getPassword(){
        return password;
    }
    public String getWebsite(){
        return website;
    }
} //End Subclass Account