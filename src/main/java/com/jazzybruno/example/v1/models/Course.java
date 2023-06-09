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
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long courseId;
    @NotNull
    @Column
    private String name;
    @NotNull
    @Column
    private String code;
    @NotNull
    @Column
    private int minStudent;
    @NotNull
    @Column
    private int maxStudent;
    @NotNull
    @Column
    private Date start;
    @NotNull
    @Column
    private Date end;
    @NotNull
    @Column
    private boolean isCancelled;



    public Course(String name, String code, int minStudent, int maxStudent, Date start, Date end, boolean isCancelled) {
        this.name = name;
        this.code = code;
        this.minStudent = minStudent;
        this.maxStudent = maxStudent;
        this.start = start;
        this.end = end;
        this.isCancelled = isCancelled;
    }
}
