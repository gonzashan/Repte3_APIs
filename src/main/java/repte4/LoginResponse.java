package repte4;


// POJO that returns for Login

public class LoginResponse {

    private String jws;
    private String error;

    public LoginResponse(String jws, String error) {
        this.jws = jws;
        this.error = error;
    }

    public String getJws() {
        return jws;
    }

    public void setJws(String jws) {
        this.jws = jws;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
    
    
}
