package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateCourseDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateCourseDTO;
import com.jazzybruno.example.v1.models.Course;
import com.jazzybruno.example.v1.models.Instructor;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.CourseRepository;
import com.jazzybruno.example.v1.repositories.InstructorRepository;
import com.jazzybruno.example.v1.services.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Override
    public ResponseEntity<ApiResponse> getAllCourses() {
        try {
            List<Course> courseList = courseRepository.findAll();
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully fetched all the courses",
                            courseList
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
    public ResponseEntity<ApiResponse> getCourseById(Long courseId) {
        if (courseRepository.existsById(courseId)){
            try {
                Course course = courseRepository.findById(courseId).get();
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully fetched the course with id: " + courseId,
                                course
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
                            "The course with id: " + courseId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createCourse(CreateCourseDTO createCourseDTO) {
            try {
                Course course = new Course(
                        createCourseDTO.getName(),
                        createCourseDTO.getCode(),
                        createCourseDTO.getMinStudent(),
                        createCourseDTO.getMaxStudent(),
                        createCourseDTO.getStart(),
                        createCourseDTO.getEnd(),
                        createCourseDTO.isCancelled()
                );

                courseRepository.save(course);

                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully Saved the Course",
                                course
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
    public ResponseEntity<ApiResponse> createManyCourses(List<CreateCourseDTO> createCourseDTOS) {
        try {
            int i = 0;
            List<Course> courseList = new ArrayList<Course>();
             while (i<createCourseDTOS.size()){
                 Course course = new Course(
                         createCourseDTOS.get(i).getName(),
                         createCourseDTOS.get(i).getCode(),
                         createCourseDTOS.get(i).getMinStudent(),
                         createCourseDTOS.get(i).getMaxStudent(),
                         createCourseDTOS.get(i).getStart(),
                         createCourseDTOS.get(i).getEnd(),
                         createCourseDTOS.get(i).isCancelled()
                 );
                 courseList.add(course);
                 i++;
             }
            courseRepository.saveAll(courseList);
             return ResponseEntity.ok().body(
                     new ApiResponse(
                             true,
                             "Successfully created the courses",
                             courseList
                     )
             );
        }catch (Exception exception){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            false,
                            exception.getMessage()
                    )
            );
        }
    }


    public void courseMapper(Course course , UpdateCourseDTO updateCourseDTO ){
        course.setCancelled(updateCourseDTO.isCancelled());
        course.setEnd(updateCourseDTO.getEnd());
        course.setStart(updateCourseDTO.getStart());
        course.setMaxStudent(updateCourseDTO.getMaxStudent());
        course.setMinStudent(updateCourseDTO.getMinStudent());
        course.setCode(updateCourseDTO.getCode());
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> updateCourse(Long courseId, UpdateCourseDTO updateCourseDTO) {
        if(courseRepository.existsById(courseId)){
            Course course = courseRepository.findById(courseId).get();
                try {
                    courseMapper(course , updateCourseDTO );

                    return ResponseEntity.ok().body(
                            new ApiResponse(
                                    true,
                                    "Successfully Updated the Course",
                                    course
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
                            "The course with id: " + courseId + " does not exist"
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> deleteCourse(Long courseId) {
        if (courseRepository.existsById(courseId)){
            Course course = courseRepository.findById(courseId).get();
            try {
                courseRepository.deleteById(courseId);
                return ResponseEntity.ok().body(
                        new ApiResponse(
                                true,
                                "Successfully deleted the course",
                                course
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
                            "The course with id: " + courseId + " does not exist"
                    )
            );
        }
    }
}
