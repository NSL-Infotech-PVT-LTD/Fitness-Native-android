package com.netscape.utrain.model;

import com.netscape.utrain.StripeConnect.StripeModel;

public class LoginRoleModel {

    private String message;
    private String token;
    private LoginChildModel user;
    private StripeModel stripeDetails;

    public StripeModel getStripeDetails() {
        return stripeDetails;
    }

    public void setStripeDetails(StripeModel stripeDetails) {
        this.stripeDetails = stripeDetails;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginChildModel getUser() {
        return user;
    }

    public void setUser(LoginChildModel user) {
        this.user = user;
    }
}
