package java3finalproject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;
/** 
 * @Course: SDEV 450 ~ Enterprise Java Programming
 * @Contributors: Charlotte Hirschberger
 * @Created Date: October 16, 2016
 * @Description: This class is called by the main JavaFX class and contains methods for hashing,
 * which is used to secure the user's database and KeyStore passwords.
 */

public class CryptoHash {
    
    private static final Random RANDOM = new SecureRandom();    // Seed for salt
    
    // Future update: compute hash length based on salt size
    private static final int HASH_LENGTH = 44;                  // use to extract hash substring from hash/salt string
    
    // default constructor
    public CryptoHash()
    {
    }
    
    /**
     * Creates a new salt and uses it to call hashString(String unhashed, byte[] salt, String algorithm)
     * @param unhashed user's choice of password during account creation
     * @return 
     * @throws java.security.NoSuchAlgorithmException 
     * @throws java.io.UnsupportedEncodingException 
     */
    public String getHashString(String unhashed) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        byte[] salt = getSalt();  // Make a new salt array 
        
        // Convert the bytes to a string so they can be stored in User table
        String saltString = Base64.getEncoder().encodeToString(salt);
        
        // Concatenate the hash and salt
        String hashed = getHashString(unhashed, salt, "SHA-256") + saltString;
        
        return hashed;
    }
    
    /**
     * Uses an existing salt to hash a user's entry
     * @param unhashed User's password entry during login
     * @param salt Byte array retrieved from User table
     * @param algorithm The algorithm to use for this hash
     * @return Hash substring concatenated with Salt substring
     * 
     * Future update: make independent of charset?, store Instance in attribute
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public String getHashString(String unhashed, byte[] salt, String algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        String hashed = "";
        
            MessageDigest md = MessageDigest.getInstance(algorithm);
            md.reset();         // empty the digest buffer
            md.update(salt);    // put the salt array in the buffer
            
            // Add the user's entry to the buffer and hash it
            byte[] unhashedBytes = md.digest(unhashed.getBytes("UTF-8"));
            
            // Convert the hash to a string and stick the salt on the end
            hashed = new String(Base64.getEncoder().encode(unhashedBytes));
        
        return hashed;
    }

    /**
     * Creates a byte[] array with a random seed
     * Future updates: store byte size in constant
     * @return 
     */
    private byte[] getSalt() 
    {
        byte[] salt = new byte[32];
        RANDOM.nextBytes(salt);
        return salt;
    }
    
    /**
     * Hashes and salts the user password entry to determine whether it matches
     * the database hash. If it's a match, return true to signal that login should
     * proceed.
     * Future update: eliminate encoding of this salt in hashString
     * @param pwEntry User's entry during login
     * @param pwHash Hash retrieved from database
     * @param salt Salt retrieved from database
     * @return true or false
     * @throws java.security.NoSuchAlgorithmException
     * @throws java.io.UnsupportedEncodingException
     */
    public boolean isExpectedPassword(String pwEntry, String pwHash, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException
    {
        boolean b = false;
        byte[] byteSalt = Base64.getDecoder().decode(salt);
        
        /*Hash the password entry and then use substring() to separate the hash
        from the salt in the returned string*/
        String currHash = getHashString(pwEntry, byteSalt, "SHA-256");
        
        if(currHash.equals(pwHash))
        {
            b = true; // Passwords match!
        }
        
        return b;
    }
}
