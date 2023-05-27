package com.jazzybruno.example.v1.dto.requests;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEmployeeDTO {
    private String name;

    private String code;

    private String JobTitle;

    private Date dob;

    private Long departmentId;
}
