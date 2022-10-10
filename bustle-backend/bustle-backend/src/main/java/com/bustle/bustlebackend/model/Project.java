package com.bustle.bustlebackend.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Project extends BaseEntity {

    @Id
    private int projectId;

    private String name;

    private String description;

    private String tldr;

    private String tasks;

    private String status;
}
