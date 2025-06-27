package xyz.leobellier.user.entities;

public class LoginResponse {
    private String jwtToken;
    private long jwtExpiration;

    public LoginResponse setToken(String jwtToken) {
        this.jwtToken = jwtToken;
        return this;
    }

    public LoginResponse setExpirationIn(long jwtExpiration) {
        this.jwtExpiration=jwtExpiration;
        return this;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public long getJwtExpiration() {
        return jwtExpiration;
    }
}
