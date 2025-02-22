package org.example.data.entity;

public class EquipmentRequest {
    private Integer requestId;
    private String request;
    private Integer requestedBy;
    public EquipmentRequest() { }

    public EquipmentRequest(Integer requestId, String request, Integer requestedBy) {
        this.requestId = requestId;
        this.request = request;
        this.requestedBy = requestedBy;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Integer getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(int requestedBy) {
        this.requestedBy = requestedBy;
    }
}

