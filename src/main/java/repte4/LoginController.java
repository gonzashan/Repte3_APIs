package repte4;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACVerifier;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

// Class implements REST services to communicate to some server
@RestController
public class LoginController {

    // ArrayList to manage users on the app
    private ArrayList<User> users = new ArrayList<>();

    // Annotation to initialize server with users we stored
    @PostConstruct
    public void initialize() throws IOException {
        Gson gson = new Gson();

        // Create a reader to get users stored on .json file
        Reader reader = Files.newBufferedReader(Paths.get("user.json"));

        // convert JSON string to User object
        Type userListType = new TypeToken<ArrayList<User>>(){}.getType();

        users = gson.fromJson(reader, userListType);
        //Check the list pn console.
        System.out.println(users);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody User user) throws Exception {

        if (Utils.checkLogin(users,user)) {
            // Returns a token from the user logged
            return ResponseEntity.status(HttpStatus.OK).body(new LoginResponse(Utils.createJWS(user), null));
        } else {
            // Return 401
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponse(null, "Bad login. User or Password"));
        }
    }

    // Returns a Java Object with errors if has been produced
    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody User newUser) throws Exception {

        System.out.println(newUser);

        if (Utils.checkUserDataFormat(users, newUser)) {
            //Encrypting password user before storing users.
            newUser.setPassword(Utils.encrypt(newUser.getPassword(), null));
            users.add(newUser);
            try {
                FileWriter file = new FileWriter("user.json");
                String jsonText = new Gson().toJson(users);
                file.write(jsonText);
                file.close();

            } catch (IOException e) {
                System.out.println("Ups!. Something goes wrong.");
                e.printStackTrace();
            }

            // Postman response 201, User created
            return ResponseEntity.status(HttpStatus.CREATED).body(new SignupResponse("OK."));

        } else {
            //Formato incorrecto, 400 Bad Request
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new SignupResponse("Password, UserName or Email fails."));
        }
    }

    // Get the number of users registered, verifying if is an authorized user
    @GetMapping("/users/count")
    public ResponseEntity<AmountUsers> amountUsers(@RequestHeader("Authorization") String authorization) {

        if (authorization != null && !authorization.equals("")) {
            // Signature (JWS) and decryption (JWE) algorithm.
            JWSObject jwsObject;
            try {
                jwsObject = JWSObject.parse(authorization.split(" ")[1]);
                JWSVerifier verifier = new MACVerifier(Utils.PASSWORD_CIPHER.getBytes());

                if (jwsObject.verify(verifier) && jwsObject.getPayload().toJSONObject().get("user").equals("superadmin")) {

                    //
                    return ResponseEntity.status(HttpStatus.OK).body(new AmountUsers(this.users.size()));
                    //return ResponseEntity.status(HttpStatus.OK).body(Utils.anonymizer(this.users));

                } else {

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // Get the list of users registered, verifying if is an authorized user
    @GetMapping("/users")
    public ResponseEntity<ArrayList<User>> listUsers(@RequestHeader("Authorization") String authorization) {

        if (authorization != null && !authorization.equals("")) {
            // Signature (JWS) and decryption (JWE) algorithm.
            JWSObject jwsObject;
            try {
                jwsObject = JWSObject.parse(authorization.split(" ")[1]);
                JWSVerifier verifier = new MACVerifier(Utils.PASSWORD_CIPHER.getBytes());

                if (jwsObject.verify(verifier) && jwsObject.getPayload().toJSONObject().get("user").equals("superadmin")) {

                    return ResponseEntity.status(HttpStatus.OK).body(Utils.anonymizer(this.users));

                } else {

                    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
                }

            } catch (Exception ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
