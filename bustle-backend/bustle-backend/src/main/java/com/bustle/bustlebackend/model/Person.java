package com.bustle.bustlebackend.model;

import com.bustle.hackto2022.annotation.FieldsValueMatch;
import com.bustle.hackto2022.annotation.PasswordValidator;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@Entity
@FieldsValueMatch.List({
        @FieldsValueMatch(
                field = "pwd",
                fieldMatch = "confirmPwd",
                message = "Passwords do not match!"
        ),
        @FieldsValueMatch(
                field = "email",
                fieldMatch = "confirmEmail",
                message = "Email addresses do not match!"
        )
})
public class Person extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
    @GenericGenerator(name = "native",strategy = "native")
    private int personId;

    @NotBlank(message="Name must not be blank")
    @Size(min=2, message="Name must be at least 2 characters long")
    private String name;

    @NotBlank(message="Name must not be blank")
    @Size(min=2, message="Name must be at least 2 characters long")
    private String headline;

//    private Blob photo;

    private String title;

    private String skills;

    private String hobbies;

    private String techinterests;

    private String buzzwords;

    private String workstyles;

    @NotBlank(message="Email must not be blank")
    @Email(message = "Please provide a valid email address" )
    private String email;

    @NotBlank(message="Confirm Email must not be blank")
    @Email(message = "Please provide a valid confirm email address" )
    @Transient
    private String confirmEmail;

    @NotBlank(message="Password must not be blank")
    @Size(min=5, message="Password must be at least 5 characters long")
    @PasswordValidator
    private String pwd;

    @NotBlank(message="Confirm Password must not be blank")
    @Size(min=5, message="Confirm Password must be at least 5 characters long")
    @Transient
    private String confirmPwd;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Roles.class)
    @JoinColumn(name = "role", referencedColumnName = "role", nullable = true)
    private Roles roles;

    @OneToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL, targetEntity = Address.class)
    @JoinColumn(name = "address_id", referencedColumnName = "addressId",nullable = true)
    private Address address;

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST, targetEntity = Project.class)
//    @JoinTable(name = "person_project",
//            joinColumns = {
//                    @JoinColumn(name = "person_id", referencedColumnName = "personId")},
//            inverseJoinColumns = {
//                    @JoinColumn(name = "project_id", referencedColumnName = "projectId")})
//
//    private Set<Project> project = new HashSet<>();

}
