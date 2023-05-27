package com.jazzybruno.example.v1.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UpdateInstructorDTO {
    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String phoneNumber;

    private double salary;

    private Date remunerationDate;

    private Long courseId;
}
