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
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
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
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//Begin Subclass AEScrypt
public class AEScrypt {
    private final String ks_name;                 // KeyStore file
    private static final String EN_ALGO = "AES";                        // Encryption algorithm
    private static final String CIPHER_INST = "AES/CBC/PKCS5Padding";   // Cipher used
    private static final String KS_INSTANCE = "JCEKS";                    // KeyStore type
    private final char[] password;
    private static final int IV_SIZE = 16;
    private static final int KEY_SIZE = 128;
    
    /**
     * Charlotte's code 
     * Gather the hash created during Login and stored in User attribute (for use as KeyStore password)
     */
    public AEScrypt() {
        password = Login.currUser.getKSPass().toCharArray();
        ks_name = "output_" + Login.currUser.getUsername() + ".jceks";
    }
    /*******End Charlotte's********/

    /*********Start Bill's*********/
    /**
     * Generates an encryption key using SecureRandom
     */
    private SecretKeySpec setKey() {
        MessageDigest sha;
        SecretKeySpec sks = null;
        try {
            KeyGenerator kg = KeyGenerator.getInstance(EN_ALGO);
            kg.init(KEY_SIZE);
            Key key = kg.generateKey();
            sks = new SecretKeySpec(key.getEncoded(), EN_ALGO);
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sks;
        /*catch (UnsupportedEncodingException e) {
         e.printStackTrace();
         }*/
    }
    
    public String encrypt(String strToEncrypt, Key key) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, 
            IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException, KeyStoreException
    {
        SecretKeySpec sks = new SecretKeySpec(key.getEncoded(), EN_ALGO);
        String encryption = encrypt(strToEncrypt, sks);
        return encryption;
    }
    
    public String encrypt(String strToEncrypt, String alias)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, 
            IllegalBlockSizeException, InvalidAlgorithmParameterException, BadPaddingException, KeyStoreException, CertificateException
    {
        SecretKeySpec sks = setKey();
        storeKey(alias, sks);
        String encryption = encrypt(strToEncrypt, sks);
        return encryption;
    }

    /**
     * encrypt method:
     *
     * @param strToEncrypt
     * @param alias
     * @return
     * @throws java.security.NoSuchAlgorithmException
     * @throws javax.crypto.NoSuchPaddingException
     * @throws java.security.InvalidKeyException
     * @throws java.io.IOException
     * @throws java.security.InvalidAlgorithmParameterException
     * @throws javax.crypto.IllegalBlockSizeException
     * @throws javax.crypto.BadPaddingException
     * @throws java.security.KeyStoreException
     * @throws java.security.cert.CertificateException
     */
    private String encrypt(String strToEncrypt, SecretKeySpec sks) 
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, 
            IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException
    {                        
            // Generate an initialisation vector, so repeated text doesn't produce identical encryptions           
            byte[] iv = generateIv();

            IvParameterSpec ivParam = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance(CIPHER_INST);
            cipher.init(Cipher.ENCRYPT_MODE, sks, ivParam);

            // Encrypt a String after converting it to bytes
            byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));

            // Prepare byte array for holding encrypted text & IV bytes
            byte[] encryptionWithIv = new byte[IV_SIZE + encrypted.length];

            // Fill encryptionWithIv indices 0-16 exclusive with content from iv, starting at index 0
            System.arraycopy(iv, 0, encryptionWithIv, 0, IV_SIZE);

            // Fill encryptionWithIv indices 16-32 exclusive with content from encrypted, starting at index 0
            System.arraycopy(encrypted, 0, encryptionWithIv, IV_SIZE, encrypted.length);

            // change encryptionWithIV to base 64
            return Base64.getEncoder().encodeToString(encryptionWithIv);
    }

    /**
     * decrypt method:
     *
     * @param strToDecrypt
     * @param alias
     * @return
     */
    public String decrypt(String strToDecrypt, String alias) 
        throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException,
        InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, KeyStoreException,
        CertificateException, UnrecoverableKeyException, NullPointerException
    {
            // Load the key from KeyStore
            Key key = loadKey(alias);
            if(key != null)
            {
                SecretKeySpec sks = new SecretKeySpec(key.getEncoded(), EN_ALGO);
            
                // Convert the encryption from text to bytes
                byte[] encryptionWithIv = Base64.getDecoder().decode(strToDecrypt);
            
                // Extract the Iv bytes from the start of the encryption
                byte[] iv = Arrays.copyOf(encryptionWithIv, IV_SIZE);
                IvParameterSpec ivParam = new IvParameterSpec(iv);
            
                // Extract the encryption, minus the Iv bytes
                    
                byte[] encryptedText = Arrays.copyOfRange(encryptionWithIv, IV_SIZE, encryptionWithIv.length);
            
                Cipher cipher = Cipher.getInstance(CIPHER_INST);
                cipher.init(Cipher.DECRYPT_MODE, sks, ivParam);
                return new String(cipher.doFinal(encryptedText));
            }
            else
            {
                throw new NullPointerException("KeyStore did not find an entry with this account name.");
            }
    }
    /*********End Bill's*********/
    
    /*********Start Charlotte's*********/
    
    private byte[] generateIv()
    {
        byte[] iv = new byte[IV_SIZE];  // create the byte array that holds the IV
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);           // load the array with random bytes

        return iv;
    }

    /**
     * Load the key store and set the key in an entry identified by the alias/account name
     * @param alias                 // Account name used to find correct KeyEntry
     * @param key
     * @throws KeyStoreException
     * @throws IOException
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     */
    public void storeKey(String alias, Key key) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        File ks_file = new File(ks_name);
        if (ks_file.exists()) {
            try (FileInputStream fis = new FileInputStream(ks_file)) {
                ks.load(fis, password);
            }
        } else {
            ks.load(null, password);
        }
        ks.setKeyEntry(alias.toLowerCase(), key, password, null);
        try (FileOutputStream fos = new FileOutputStream(ks_file)) {
            ks.store(fos, password);
        }
    }

    /**
     * Load the KeyStore and retrieve the Key from the entry with the provided alias
     *
     * @param alias
     * @return 
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     * @throws UnrecoverableKeyException
     */
    public Key loadKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException
    {
        Key key = null;
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);

        try (FileInputStream fis = new FileInputStream(ks_name)) {
            ks.load(fis, password);
            
            key = ks.getKey(alias.toLowerCase(), password);
        }
        return key;
    }

    /**
     * Load the KeyStore and delete the KeyEntry identified by the provided alias
     *
     * @param alias
     * @throws KeyStoreException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws CertificateException
     */
    public void deleteKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        try (FileInputStream fis = new FileInputStream(ks_name)) {
            ks.load(fis, password);
        }
        ks.deleteEntry(alias.toLowerCase());
        try(FileOutputStream fos = new FileOutputStream(ks_name))
        {
            ks.store(fos, password);
        }
    }
    /*********End Charlotte's*********/
} //End Subclass AEScrypt
