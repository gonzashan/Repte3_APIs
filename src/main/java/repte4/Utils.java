package repte4;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {

    public static final int GCM_IV_LENGTH = 12;
    private static final String PASSWORD = "01234567890123456789012345678901";
    public static final String PASSWORD_CIPHER = "01234567890123456789012345678901";

    private static final SecureRandom secureRandom = new SecureRandom();

    // Hide sensitive data from users
    public static ArrayList<User> anonymizer(ArrayList<User> users) {

        char[] strChars;
        ArrayList<User> usersChanged = new ArrayList<>();

        for (User user : users) {
            //Anonymization of emails
            String email = user.getEmail();
            strChars = email.toCharArray();
            String head = email.split("@")[0];
            String tail = email.split("@")[1];
            String tail2 = tail.split("[.]")[0];

            for (int i = 2; i < head.length() - 1; i++) {
                strChars[i] = '*';
            }
            for (int i = head.length() + 2; i < (head.length() + (tail2.length())); i++) {
                strChars[i] = '*';
            }

            User userChanged = new User(user.getFullName(), user.getUserName(),"***", String.valueOf(strChars));
            usersChanged.add(userChanged);
        }

        return usersChanged;
    }
    // Avoiding repeating records and the policy about password
    public static boolean checkUserDataFormat(ArrayList<User> users, User user) {

        // Check if userName is registred
        boolean isNameRegistred = users.stream()
                .anyMatch(a -> a.getUserName().equalsIgnoreCase(user.getUserName()));
        // Check if Email is registred yet
        boolean isEmailRegistred = users.stream()
                .anyMatch(a -> a.getEmail().equalsIgnoreCase(user.getEmail()));
        // Check if password typed complies with requirements.
        boolean hasSpecialChars = PasswordValidator.isValid(user.getPassword());

        return !isNameRegistred && !isEmailRegistred && hasSpecialChars;
    }

    public static boolean checkLogin(ArrayList<User> users, User user) throws Exception {

        boolean logged = false;
        for (int i = 0; i < users.size() && !logged; i++) {
            if (users.get(i)
                    .getUserName().equals(user.getUserName()) &&
                    Utils.decrypt(users.get(i)
                            .getPassword(),null).equals(user.getPassword()))
                logged = true;
        }//End for

        return logged;
    }

    // Encrypting info user to storing at .json file
    public static String encrypt(String plaintext, byte[] associatedData) throws Exception {

        SecretKey secretKey = new SecretKeySpec(PASSWORD.getBytes(), "AES");
        byte[] iv = new byte[GCM_IV_LENGTH]; //NEVER REUSE THIS IV WITH SAME KEY
        secureRandom.nextBytes(iv);
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        GCMParameterSpec parameterSpec = new GCMParameterSpec(128, iv); //128 bit auth tag length
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

        if (associatedData != null) {
            cipher.updateAAD(associatedData);
        }

        byte[] cipherText = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

        ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + cipherText.length);
        byteBuffer.put(iv);
        byteBuffer.put(cipherText);
        return Base64.getEncoder().encodeToString(byteBuffer.array());
        //return byteBuffer.array();
    }

    // Decrypting info user stored at .json file
    public static String decrypt(String cipherMessage, byte[] associatedData) throws Exception {

        byte[] cifrado = Base64.getDecoder().decode(cipherMessage);

        SecretKey secretKey = new SecretKeySpec(PASSWORD.getBytes(), "AES");
        final Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        //use first 12 bytes for iv
        AlgorithmParameterSpec gcmIv = new GCMParameterSpec(128, cifrado, 0, GCM_IV_LENGTH);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmIv);

        if (associatedData != null) {
            cipher.updateAAD(associatedData);
        }
        //use everything from 12 bytes on as ciphertext
        byte[] plainText = cipher.doFinal(cifrado, GCM_IV_LENGTH, cifrado.length - GCM_IV_LENGTH);

        return new String(plainText, StandardCharsets.UTF_8);
    }

    // Creates a JSON Web Signature encoding in base 64
    public static String createJWS(User user) {

        JWSObject jwsObject = new JWSObject (new JWSHeader(JWSAlgorithm.HS256),
                new Payload("{" + "\"user\":\"" + user.getUserName() + "\", \"role\":" + "\"user\"" + "}"));

        try {
            jwsObject.sign(new MACSigner(PASSWORD_CIPHER.getBytes()));
        } catch (KeyLengthException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JOSEException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return jwsObject.serialize();
    }

}
