package com.jazzybruno.example.v1.dto.requests;

import com.jazzybruno.example.v1.models.Instructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Scope;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateCourseDTO {
    private String name;

    private String code;

    private int minStudent;

    private int maxStudent;

    private Date start;

    private Date end;

    private boolean isCancelled;

    private Long instructorId;
}
