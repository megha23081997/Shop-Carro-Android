package com.example.meghamoondra.navigationbar.model;

import java.util.List;
public class NewOrderProduct {
    private String email;
    private List<NewOrderDetails> details;
    public NewOrderProduct() {
    }
    public NewOrderProduct(String email, List<NewOrderDetails> details) {
        this.email = email;
        this.details = details;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<NewOrderDetails> getDetails() {
        return details;
    }
    public void setDetails(List<NewOrderDetails> details) {
        this.details = details;
    }
    @Override
    public String toString() {
        return "NewOrderProduct{" +
                "email='" + email + '\'' +
                ", details=" + details +
                '}';
    }
}
