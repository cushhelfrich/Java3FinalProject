package Java3FinalProject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Author Name: Charlotte Hirschberger
 * @Created Date: October 16, 2016
 * @Description: This class will be called by the main JavaFX class. It will contain methods for hashing, 
 *      AES encryption, and so on. Currently contains hashString and getSalt methods.
 */

public class Encryptor {
    private static final Random RANDOM = new SecureRandom();
    
    // default constructor
    public Encryptor()
    {
    }
    
    // make independent of charset?
    public String hashString(String unhashed, byte[] salt)
    {
        String hashed = "";
        
        try 
        {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.reset();
            md.update(salt);
            byte[] unhashedBytes = md.digest(unhashed.getBytes("UTF-8"));
            hashed = new String(Base64.getEncoder().encode(unhashedBytes));
        }
        catch(NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        
        return hashed;
    }

    public byte[] getSalt() 
    {
        byte[] salt = new byte[32];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
    public boolean isExpectedPassword(String pwEntry, String pwHash, byte[] salt)
    {
        boolean b = false;
        String currHash = hashString(pwEntry, salt);
        
        if(currHash.equals(pwHash))
        {
            b = true;
        }
        
        return b;
    }
    

}
