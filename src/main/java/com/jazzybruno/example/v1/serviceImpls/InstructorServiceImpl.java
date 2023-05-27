package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.models.Course;
import com.jazzybruno.example.v1.models.Instructor;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.CourseRepository;
import com.jazzybruno.example.v1.repositories.InstructorRepository;
import com.jazzybruno.example.v1.services.InstructorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InstructorServiceImpl implements InstructorService {
    private final InstructorRepository instructorRepository;
    private final CourseRepository courseRepository;

    @Override
    public ResponseEntity<ApiResponse> getAllInstructors() {
        try {
            List<Instructor> instructors = instructorRepository.findAll();
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully fetched all the instructors",
                            instructors
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
    public ResponseEntity<ApiResponse> getInstructorById(Long instructorId) {
        if (instructorRepository.existsById(instructorId)){
            try {
                Instructor instructor = instructorRepository.findById(instructorId).get();
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully fetched the instructor with id: " + instructorId,
                                instructor
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
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The instructor with id: " + instructorId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createInstructor(CreateInstructorDTO createInstructorDTO) {
        if (courseRepository.existsById(createInstructorDTO.getCourseId())){
            try {
                Course course = courseRepository.findById(createInstructorDTO.getCourseId()).get();
                Instructor instructor = new Instructor(
                        createInstructorDTO.getFirstName(),
                        createInstructorDTO.getLastName(),
                        createInstructorDTO.getDateOfBirth(),
                        createInstructorDTO.getPhoneNumber(),
                        createInstructorDTO.getSalary(),
                        createInstructorDTO.getRemunerationDate(),
                        course
                );

                instructorRepository.save(instructor);

                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully created the instructors",
                                instructor
                        )
                );
            }catch (Exception e){
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,e.getMessage()
                        )
                );
            }
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The course with id: " + createInstructorDTO.getCourseId() + " does not exist"
                    )
            );
        }
    }

    public void instructorMapper(Instructor instructor , UpdateInstructorDTO updateInstructorDTO , Course course){
        instructor.setFirstName(updateInstructorDTO.getFirstName());
        instructor.setLastName(updateInstructorDTO.getLastName());
        instructor.setSalary(updateInstructorDTO.getSalary());
        instructor.setPhoneNumber(updateInstructorDTO.getPhoneNumber());
        instructor.setDateOfBirth(updateInstructorDTO.getDateOfBirth());
        instructor.setRemunerationDate(updateInstructorDTO.getRemunerationDate());
        instructor.setCourse(course);
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> updateInstructor(Long instructorId, UpdateInstructorDTO updateInstructorDTO) {
        if (instructorRepository.existsById(instructorId)){
            if (courseRepository.existsById(updateInstructorDTO.getCourseId())){
                try {
                    Course course = courseRepository.findById(updateInstructorDTO.getCourseId()).get();
                    Instructor instructor = instructorRepository.findById(instructorId).get();
                    instructorMapper(instructor , updateInstructorDTO , course);
                    return ResponseEntity.ok().body(
                            new ApiResponse(
                                    true,
                                    "Successfully updated the instructor with id: " + instructorId,
                                    instructor
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
                return ResponseEntity.status(404).body(
                        new ApiResponse(
                                false,
                                "The course with id: " + updateInstructorDTO.getCourseId() + " does not exist"
                        )
                );
            }
        }else {
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The instructor with id: " + instructorId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteInstructor(Long instructorId) {
        if (instructorRepository.existsById(instructorId)){
            try {
                Instructor instructor = instructorRepository.findById(instructorId).get();
                instructorRepository.delete(instructor);
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully deleted the instructor with id: " + instructorId,
                                instructor
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
            return ResponseEntity.status(404).body(
                    new ApiResponse(
                            false,
                            "The instructor with id: " + instructorId + " does not exist"
                    )
            );
        }
    }
}
