package net.ricebean.railroad.controller.model;

import net.ricebean.railroad.controller.model.types.Status;

public class DccStatus {


    private final Status status;

    /**
     * Custom constructor
     */
    public DccStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
