package org.example.data.entity;

public class Users {
    private String password;
    // Mandatory Fields
    private Integer userId;
    private String firstName;
    private String lastName;
//    private String password;
    private String email;

    // Optional Fields
    private Long phone;
    private Boolean isTenant;

    // Constructors
    public Users(Integer userId, String firstName, String lastName, String password, String email, Long phone, Boolean isTenant) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.isTenant = isTenant;
    }

    public Users(Integer userId, String firstName, String lastName, String password, String email) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
    }

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
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

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public Boolean getIsTenant() {
        return isTenant;
    }

    public void setIsTenant(Boolean isTenant) {
        this.isTenant = isTenant;
    }

    @Override
    public String toString() {
        return "Users{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                ", isTenant=" + isTenant +
                '}';
    }
}
