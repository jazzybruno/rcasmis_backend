package com.jazzybruno.example.v1.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @NotNull
    @Column
    private String firstName;
    @NotNull
    @Column
    private String lastName;
    @NotNull
    @Column
    private Date dateOfBirth;
    @NotNull
    @Column
    private String email;
    @NotNull
    @Column
    private boolean isInternational;
    @NotNull
    @Column
    private boolean isPartTime;
    @NotNull
    @Column
    private boolean isRepeating;

    public Student(String firstName, String lastName, Date dateOfBirth, String email, boolean isInternational, boolean isPartTime, boolean isRepeating) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.isInternational = isInternational;
        this.isPartTime = isPartTime;
        this.isRepeating = isRepeating;
    }
}
