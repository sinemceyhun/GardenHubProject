package org.example.data.repository;

public class Stat {
    private String fullName;
    private Integer total;

    public Stat(String fullName, Integer total) {
        this.fullName = fullName;
        this.total = total;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
