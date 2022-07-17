package repte4;

import java.util.ArrayList;

// POJO that returns for crear users

public class User {
    private String fullName;
    private String userName;
    private String password;
    private String email;
    static ArrayList<User> users = new ArrayList<>();

    public User() {
    }

    public User(String fullName, String userName, String password, String email) {
        this.fullName = fullName;
        this.userName = userName;
        this.password = password;
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" + "fullName:'" + fullName + '\'' +
                ", username:'" + userName + '\'' +
                ", password:'" + password + '\'' +
                ", email:'" + email + '\'' +
                '}' + "\n";
    }






  /* Battlefield testing

    public static void main(String[] args) throws Exception {
        User user = new User("Francisco Fiasco", "pako", "1234", "melon@melones.es");
        User user1 = new User("Luís Angel Gómez", "luis", "897", "sofisticado68@gmail.com");
        User user2 = new User("Anabel Alonso", "ana", "mentira$cochina", "fresas@outlook.com");
        users.add(user);
        users.add(user1);
        users.add(user2);


        User userLogin = new User("Ahinoa Arteta", "ainhoa", "loFg*+.-d,1", "cabezon@melones.es");
        User userLoginFake = new User("Ahinoa Arteta", "ainhoak", "loFg*+.-d,1", "cabezon@melones.es");

        users.add(userLogin);
//        System.out.println(anonymizer(users));
//        System.out.println(users);

        String passando = "nosabesquiensoy";
        //String cifrado = Utils.encrypt(passando,null);
        String cifrado = "fTBXKfO0mXUR6cmEngd/oAODrOOsyqQqBUNUKC4Pfi5Ignff6vZCkHd3cg==";
        System.out.println(PasswordValidator.isValid("loFg*+.-d,1"));
//        System.out.println(cifrado);
//        System.out.println(Utils.decrypt(cifrado,null));
        String jsonText = new Gson().toJson(users);
        //System.out.println(jsonText);

    }
            */

}

