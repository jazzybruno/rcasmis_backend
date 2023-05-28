package com.jazzybruno.example.v1.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long instructorId;
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
    private String phoneNumber;
    @NotNull
    @Column
    private double salary;
    @NotNull
    @Column
    private Date remunerationDate;
    @ManyToOne
    @JoinColumn(name = "course")
    private Course course;

    private String photoId;

    public Instructor(String firstName, String lastName, Date dateOfBirth, String phoneNumber, double salary, Date remunerationDate, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.salary = salary;
        this.remunerationDate = remunerationDate;
        this.course = course;
    }
}
