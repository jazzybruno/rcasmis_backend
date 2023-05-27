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
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long employeeId;
    @NotNull
    @Column
    private String name;
    @NotNull
    @Column
    private String code;
    @NotNull
    @Column
    private String jobTitle;
    @NotNull@Column
    private Date dob;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

    public Employee(String name, String code, String jobTitle, Date dob, Department department) {
        this.name = name;
        this.code = code;
        this.jobTitle = jobTitle;
        this.dob = dob;
        this.department = department;
    }
}
