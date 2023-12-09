package model;

import lombok.Getter;

public class Token {

    @Getter
    public String token;
    public String username;
    public boolean isValid;

    public Token() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Token(String token) {
        this.token = token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setValid(boolean valid) {
        isValid = valid;
    }
}
