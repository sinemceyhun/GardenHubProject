package org.example.data.entity;

import java.sql.Date;

public class Reservation {
    private int reservationId;
    private int userId;
    private int fieldId;
    private Date startDate;
    private Date endDate;
    private String status;

    public Reservation() { }

    public Reservation(int reservationId, int userId, int fieldId, Date startDate, Date endDate, String status) {
        this.reservationId = reservationId;
        this.userId = userId;
        this.fieldId = fieldId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
    }

    public int getReservationId() {
        return reservationId;
    }

    public void setReservationId(int reservationId) {
        this.reservationId = reservationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

