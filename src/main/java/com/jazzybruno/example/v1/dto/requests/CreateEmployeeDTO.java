package com.jazzybruno.example.v1.dto.requests;

import com.jazzybruno.example.v1.models.Department;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmployeeDTO {
    private String name;

    private String code;

    private String JobTitle;

    private Date dob;

    private Long departmentId;
}
