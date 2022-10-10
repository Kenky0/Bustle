package com.bustle.bustlebackend.model;

public interface Invitation {
    long generateRequestId();

    void setStatus(String open);
}
