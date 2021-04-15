package com.kanban.model.json;

public class TokenJSON {
    private String Token;

    private Long ID;

    public TokenJSON(String token, Long ID) {
        this.Token = token;
        this.ID = ID;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }
}
