package org.example.data.entity;

public class User {
    private int userId;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private Long phone;
    private Boolean isTenant;

    public User() { }

    public User(int userId, String firstName, String lastName, String password, String email, Long phone, Boolean isTenant) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isTenant = isTenant;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public boolean isTenant() {
        return isTenant;
    }

    public void setTenant(Boolean tenant) {
        isTenant = tenant;
    }
}

