package java3finalproject;

/**
 * @Course: SDEV 450 ~ Java Programming III
 * @Contributors: Bill Tanona, Charlotte Hirschberger
 * @Assignment Name: java3finalproject
 * @Date: Nov 3, 2016
 * @Last Update: Nov 30, 2016
 * @Subclass AEScrypt Description:
 *
 * Class implements Java AES encryption and decryption and storage and retrieval
 * of keys from a KeyStore
 *
 *
 */
//Imports
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//Begin Subclass AEScrypt
public class AEScrypt {
    private static SecretKeySpec sks;
    private static byte[] keyToSave;
    private static final String KS_FILE = "output.jks";                 // KeyStore file
    private static final String EN_ALGO = "AES";                        // Encryption algorithm
    private static final String CIPHER_INST = "AES/ECB/PKCS5Padding";   // Cipher used
    private static final String KS_INSTANCE = "JKS";                    // KeyStore type
    private static char[] password;
    private static final int IV_SIZE = 16;
    
    
    /**
     * Charlotte's code
     * Gather the hash created during Login and stored in User attribute (for
     * use as KeyStore password)
     */
    public AEScrypt()
    {
        password = Login.currUser.getKSPass().toCharArray();
    }
    /*********End Charlotte's*********/

    /*********Start Bill's*********/
    /**
     * Generates an encryption key using SecureRandom
     */
    private static void setKey() {
        MessageDigest sha = null;
        try {
            SecureRandom sr = new SecureRandom();      // Get seed
            KeyGenerator kg = KeyGenerator.getInstance(EN_ALGO);
            Key key = kg.generateKey();
            keyToSave = key.getEncoded();
            sha = MessageDigest.getInstance("SHA-1");
            sha.update(keyToSave);
            byte[] keyBytes = new byte[16];
            keyBytes = Arrays.copyOf(sha.digest(), 16);
            
            sks = new SecretKeySpec(keyBytes, EN_ALGO);
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        /*catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * encrypt method:
     *
     * @param strToEncrypt
     * @param alias
     * @param secret
     * @return
     */
    public static String encrypt(String strToEncrypt, String alias) {
        try {
            // setKey(secret);     // Generate the key
            setKey();
            
            IvParameterSpec iv = generateIv();
            // Generate an initialisation vector, so repeated text doesn't produce identical encryptions
            Cipher cipher = Cipher.getInstance(CIPHER_INST);
            cipher.init(Cipher.ENCRYPT_MODE, sks, iv);
            
            // Encrypt the data after converting it to bytes
            byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));    // Encrypt the data
            storeKey(alias);
            
            byte[] encryptionWithIv = new byte[IV_SIZE + encrypted.length];
            
            // Fill encryptionWithIv indices 0-16 exclusive with content from iv, starting at index 0
            System.arraycopy(iv, 0, encryptionWithIv, 0, IV_SIZE);
            
            // Fill encryptionWithIv indices 16-__ exclusive with content from encrypted, starting at index 0
            System.arraycopy(encrypted, 0, encryptionWithIv, IV_SIZE, IV_SIZE + encrypted.length);
            
            // What is encrypted.length?
            // change encryptionWithIV to base 64
            return Base64.getEncoder().encodeToString(encryptionWithIv);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * decrypt method:
     *
     * @param strToDecrypt
     * @param alias
     * @param secret
     * @return
     */
    public static String decrypt(String strToDecrypt, String alias) {
        try {
            // Load the key from KeyStore
            loadKey(alias);
            
            // Convert the encryption from text to bytes
            byte[] encryptionWithIv = Base64.getDecoder().decode(strToDecrypt);
            
            byte[] iv = Arrays.copyOf(encryptionWithIv, IV_SIZE);
            IvParameterSpec ivParam = new IvParameterSpec(iv);
            
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    /*********End Bill's*********/
    
    /*********Start Charlotte's*********/
    
    private static IvParameterSpec generateIv()
    {
        byte[] iv = new byte[IV_SIZE];  // create the byte array that holds the IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);           // load the array with random bytes
        IvParameterSpec ivParam = new IvParameterSpec(iv);      // create iv from the bytes
        
        return ivParam;
    }
    
    /**
     * Load the key store and set the key in an entry identified by the alias/account name
     * @param alias                 // Account name used to find correct KeyEntry
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException 
     */
    private static void storeKey(String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(null, password);
        
        // sks necessary here??
        ks.setKeyEntry(alias, sks, password, null);
        ks.store(new FileOutputStream(KS_FILE), password);
    }
    
    /**
     * Load the KeyStore and retrieve the Key from the entry with the provided alias
     * @param alias
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException 
     */
    private static void loadKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(new FileInputStream(KS_FILE), password);
        Key key = ks.getKey(alias, password);
        sks = new SecretKeySpec(key.getEncoded(), EN_ALGO);
    }

    /**
     * Load the KeyStore and delete the KeyEntry identified by the provided alias 
     * @param alias
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException 
     */
    public static void deleteKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(null, password);
        ks.deleteEntry(alias);
        // ks.store??
    }
    /*********End Charlotte's*********/
} //End Subclass AEScrypt
