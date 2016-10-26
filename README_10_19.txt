Changes made by Charlotte on 10/19:
Login.java:
-added imports to accommodate all changes below (l. 15-18)
-deleted 'placeholder' variables originally used by btnLogin
-made lblMessage, txtUserName, and pf global for use by processLogin (l. 46-48) and removed them from inside
Start method
-added Encryptor object instantiation (l. 45)
-moved code in btnLogin handler (l. 115) to separate processLogin function
-added processLogin (l. 141), which opens the connection to the projectdb database and compares the user's
password entry to the stored hash

I also added the Encryptor.java and DBConnector.java classes

*VERY IMPORTANT*
Before clicking login, make sure you have set up a database called 'projectdb' with a 'champlain' user and 'Fin8lPr0ject' password and all privileges. Make sure the database has a 'user' table with at least email, password, and salt fields