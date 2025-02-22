package org.example.data.entity;

public class Field {
    private int fieldId;
    private String fieldName;
    private int size;
    private String status;
    private double pricePerDay;
    private Integer rentedBy;

    public Field() { }

    public Field(int fieldId, String fieldName,int size, double pricePerDay) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.size = size;
        this.pricePerDay = pricePerDay;
        // this.status = status;
        //this.rentedBy = rentedBy;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePeDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Integer getRentedBy() {
        return rentedBy;
    }

    public void setRentedBy(Integer rentedBy) {
        this.rentedBy = rentedBy;
    }
}

