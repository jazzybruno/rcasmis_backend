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
public class UpdateStudentDTO {
    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String email;

    private boolean isInternational;

    private boolean isPartTime;

    private boolean isRepeating;
}
