package com.jazzybruno.example.v1.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@AllArgsConstructor
@RequiredArgsConstructor
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
    private String JobTitle;
    @NotNull@Column
    private Date dob;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "department")
    private Department department;

}
