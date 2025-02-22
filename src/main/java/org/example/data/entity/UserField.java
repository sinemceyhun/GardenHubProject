package org.example.data.entity;

import java.time.LocalDate;

public class UserField {
    private int fieldId;
    private String fieldName;
    private int size;
    private LocalDate startDate;
    private double pricePerDay;

    public UserField(int fieldId, String fieldName, int size, LocalDate startDate, double pricePerDay) {
        this.fieldId = fieldId;
        this.fieldName = fieldName;
        this.size = size;
        this.startDate = startDate;
        this.pricePerDay = pricePerDay;
    }

    // Getter ve Setter
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
}
