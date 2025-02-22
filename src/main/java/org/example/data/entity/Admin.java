package org.example.data.entity;

public class Admin {
    private int adminId;
    private String username;
    private String password;

    // Parametresiz kurucu (No-Args Constructor)
    public Admin() {}

    // Parametreli kurucu (All-Args Constructor)
    public Admin(int adminId, String username, String password) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
    }

    // Getter ve Setter metodları
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // toString metodu (Nesneyi yazdırmak için)
    @Override
    public String toString() {
        return "Admins{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

