package com.jazzybruno.example.v1.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Departments {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long departId;
    @NotNull
    @Column
    private String departName;
}
