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
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;

//Begin Subclass AEScrypt
public class AEScrypt {
    
    private static Key key;
    private static SecretKeySpec sks;
    private static byte[] keyToSave;
    private static final String KS_FILE = "output.jks";
    private static final String EN_ALGO = "AES";
    private static final String CIPHER_INST = "AES/ECB/PKCS5Padding";
    private static final String RAND_INST = "SHA1PRNG";
    private static final String KS_INSTANCE = "JKS";
    private static char[] password;
    
    public AEScrypt()
    {
        password = Login.currUser.getKSPass().toCharArray();
    }

    private static void setKey() {
        // MessageDigest sha = null;
        try {
            /*key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);*/
            
            // New implementation
            SecureRandom sr = SecureRandom.getInstance(RAND_INST);
            KeyGenerator kg = KeyGenerator.getInstance(EN_ALGO);
            key = kg.generateKey();
            
            keyToSave = key.getEncoded();
            
            sks = new SecretKeySpec(keyToSave, EN_ALGO);
            
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
     * @param secret
     * @return
     */
    public static String encrypt(String strToEncrypt, String alias) {
        try {
            // setKey(secret);     // Generate the key
            setKey();
            Cipher cipher = Cipher.getInstance(CIPHER_INST);
            cipher.init(Cipher.ENCRYPT_MODE, sks);
            byte[] encrypted = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));    // Encrypt the data
            storeKey(alias);
            return Base64.getEncoder().encodeToString(encrypted);
        } catch (Exception e) {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    /**
     * decrypt method:
     *
     * @param strToDecrypt
     * @param secret
     * @return
     */
    public static String decrypt(String strToDecrypt, String alias) {
        try {
            loadKey(alias);
            setKey();
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, sks);
            return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
        } catch (Exception e) {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
    
    private static void storeKey(String alias) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(null, password);
        ks.setKeyEntry(alias, sks, password, null);
        ks.store(new FileOutputStream(KS_FILE), password);
    }
    
    private static void loadKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException, UnrecoverableKeyException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(new FileInputStream(KS_FILE), password);
        key = ks.getKey(alias, password);
        sks = new SecretKeySpec(key.getEncoded(), EN_ALGO);
    }

    public static void deleteKey(String alias) throws KeyStoreException, IOException, NoSuchAlgorithmException, CertificateException
    {
        KeyStore ks = KeyStore.getInstance(KS_INSTANCE);
        ks.load(null, password);
        ks.deleteEntry(alias);
    }
} //End Subclass AEScrypt
