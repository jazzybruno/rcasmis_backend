package com.jazzybruno.example.v1.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
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
    private String JobTitle;
    @NotNull@Column
    private Date dob;

}
