package com.bustle.bustlebackend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Blob;

@Data
@Entity
public class EventPost extends BaseEntity {

    @Id
    private int eventId;

    private String description;

    private String tldr;

    private Blob[] photos;

    private String status;
}
