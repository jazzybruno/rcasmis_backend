package com.jazzybruno.example.v1.dto.requests;

import com.jazzybruno.example.v1.models.Instructor;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreateCourseDTO {

    private String name;

    private String code;

    private int minStudent;

    private int maxStudent;

    private Date start;

    private Date end;

    private boolean isCancelled;

}
