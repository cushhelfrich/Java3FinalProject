package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Author Name: Bill Tanona
 * @Assignment Name: java3finalproject
 * @Date: Nov 3, 2016
 * @Subclass AEScrypt Description:
 *
 * Class implements Java AES encryption and decryption
 *
 *
 */
//Imports
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

//Begin Subclass AEScrypt
public class AEScrypt {

    private static SecretKeySpec secretKey;
    private static byte[] key;
    private static String keyString;
    private static final String KS_INSTANCE = "JKS";

    public static void setKey(String myKey) {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * encrypt method:
     *
     * @param strToEncrypt
     * @param secret
     * @return
     */
    public static String encrypt(String strToEncrypt, String secret, String alias) {
        try {
            setKey(secret);     // Generate the key
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] key = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));    // Encrypt the data
            keyString = Base64.getEncoder().encodeToString(key);
            
            storeKey(alias);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }
    
    public String getKeyString()
    {
        return keyString;
    }

    /**
     * decrypt method:
     *
     * @param strToDecrypt
     * @param secret
     * @return
     */
    public static String decrypt(String strToDecrypt, String secret, String alias) {
        try {
            loadKey(alias);
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    public static void storeKey(String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        char[] password = Login.currUser.getKSPass().toCharArray();
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(null, password);
        ks.setKeyEntry(alias, secretKey, password, null);
        
        ks.store(new FileOutputStream("output.jks"), password);
    }
    
    public static void loadKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException
    {
        char[] password = Login.currUser.getKSPass().toCharArray();
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(new FileInputStream("output.jks"), password);
        
        secretKey = ks.getKey(alias, password);
        
        
    }

} //End Subclass AEScrypt
