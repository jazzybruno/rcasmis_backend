package com.jazzybruno.example.v1.dto.requests;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateStudentDTO {
    private String firstName;

    private String lastName;

    private Date dateOfBirth;

    private String email;

    private boolean isInternational;

    private boolean isPartTime;

    private boolean isRepeating;
}
