package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateStudentDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.models.Student;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.StudentRepository;
import com.jazzybruno.example.v1.services.StudentService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    public ResponseEntity<ApiResponse> getAllStudents() {
        try {
            List<Student> students = studentRepository.findAll();
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully fetched the students",
                            students
                    )
            );
        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> getStudentById(Long studentId) {
        if (studentRepository.existsById(studentId)){
           try {
               Student student = studentRepository.findById(studentId).get();
               return ResponseEntity.ok().body(
                       new ApiResponse(
                               true,
                               "Successfully fetched the student with id: " + studentId,
                               student
                       )
               );
           }catch (Exception e){
               return ResponseEntity.status(500).body(
                       new ApiResponse(
                               false,
                               e.getMessage()
                       )
               );
           }

        }else {
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            "The student with id: " + studentId + " does not exists"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createStudent(CreateStudentDTO createStudentDTO) {
    try{
       Student student = new Student(
               createStudentDTO.getFirstName(),
               createStudentDTO.getLastName(),
               createStudentDTO.getDateOfBirth(),
               createStudentDTO.getEmail(),
               createStudentDTO.isInternational(),
               createStudentDTO.isPartTime(),
               createStudentDTO.isRepeating()
       );

       studentRepository.save(student);

       return ResponseEntity.ok().body(
               new ApiResponse(
                       true,
                       "Successfully saved the student",
                       student
               )
       );
    }catch (Exception e){
        return ResponseEntity.status(500).body(
                new ApiResponse(
                        false,
                        e.getMessage()
                )
        );
    }
    }

    public void studentMapper(Student student , UpdateStudentDTO updateStudentDTO){
        student.setEmail(updateStudentDTO.getEmail());
        student.setInternational(updateStudentDTO.isInternational());
        student.setRepeating(updateStudentDTO.isRepeating());
        student.setFirstName(updateStudentDTO.getFirstName());
        student.setLastName(updateStudentDTO.getLastName());
        student.setPartTime(updateStudentDTO.isPartTime());
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> updateStudent(Long studentId, UpdateStudentDTO updateStudentDTO) {
        if (studentRepository.existsById(studentId)){
            try {
                Student student = studentRepository.findById(studentId).get();
                studentMapper(student , updateStudentDTO);
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully updated the student with id: " + studentId,
                                student
                        )
                );
            }catch (Exception e){
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,
                                e.getMessage()
                        )
                );
            }

        }else {
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            "The student with id: " + studentId + " does not exists"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteStudent(Long studentId) {
        if (studentRepository.existsById(studentId)){
            try {
                Student student = studentRepository.findById(studentId).get();
                studentRepository.delete(student);
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully deleted the student with id: " + studentId,
                                student
                        )
                );
            }catch (Exception e){
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,
                                e.getMessage()
                        )
                );
            }

        }else {
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            "The student with id: " + studentId + " does not exists"
                    )
            );
        }
    }
}
