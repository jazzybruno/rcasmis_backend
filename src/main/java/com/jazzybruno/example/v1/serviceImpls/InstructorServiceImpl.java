package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateInstructorDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.dto.responses.FileUploadResponse;
import com.jazzybruno.example.v1.models.Course;
import com.jazzybruno.example.v1.models.Instructor;
import com.jazzybruno.example.v1.models.Student;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.CourseRepository;
import com.jazzybruno.example.v1.repositories.InstructorRepository;
import com.jazzybruno.example.v1.services.InstructorService;
import com.jazzybruno.example.v1.utils.FileDownload;
import com.jazzybruno.example.v1.utils.FileUpload;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
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
    public ResponseEntity<?> getInstructorProfile(Long instructorId) throws IOException {
        if (instructorRepository.existsById(instructorId)){
            Instructor instructor = instructorRepository.findById(instructorId).get();
            String fileCode = instructor.getPhotoId();
            if(fileCode == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ApiResponse(
                                false,
                                "The Instructor with id: " + instructorId + " does not have a profile"
                        )
                );
            }else {
                FileDownload fileDownload = new FileDownload();
                Resource resource = null;
                resource = fileDownload.getFileAsResource(fileCode);
                if(resource == null){
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ApiResponse(
                                    false,
                                    "The File was not found"
                            )
                    );
                }

                String contentType = "application/octet-stream";
                String headerValue = "attachment; fileName\"" + resource.getFilename() + "\"";

                return ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType(contentType))
                        .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                        .body(
                                resource
                        );
            }

        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ApiResponse(
                            false,
                            "The Instructor with id: " + instructorId + " does not exist"
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

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> uploadInstructorPhoto(Long instructorId, MultipartFile multipartFile) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();
            if(instructorRepository.existsById(instructorId)){
                Instructor instructor = instructorRepository.findById(instructorId).get();
                if(instructor.getPhotoId() == null){
                    String fileCode = FileUpload.saveFile(fileName , multipartFile);
                    FileUploadResponse fileUploadResponse = new FileUploadResponse();
                    fileUploadResponse.setSize(size);
                    fileUploadResponse.setFileName(fileName);
                    fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                    instructor.setPhotoId(fileCode);

                    return ResponseEntity.ok().body(
                            new ApiResponse(
                                    true,
                                    "Successfully uploaded the Instructor's picture with name: " + instructor.getFirstName(),
                                    fileUploadResponse
                            )
                    );

                }else{
                    // TODO: 5/27/2023 Delete the existing and add the new photo
                    String savedFileCode = instructor.getPhotoId();
                    String filePath = FileUpload.getFile(savedFileCode);
                    System.out.println(filePath);
                    boolean isFilePresent = FileUpload.deleteFile(filePath);
                    if(isFilePresent){
                        String fileCode = FileUpload.saveFile(fileName , multipartFile);
                        FileUploadResponse fileUploadResponse = new FileUploadResponse();
                        fileUploadResponse.setSize(size);
                        fileUploadResponse.setFileName(fileName);
                        fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                        instructor.setPhotoId(fileCode);

                        return ResponseEntity.ok().body(
                                new ApiResponse(
                                        true,
                                        "Successfully uploaded the instrcutor's picture with name: " + instructor.getFirstName(),
                                        fileUploadResponse
                                )
                        );

                    }else {
                        return ResponseEntity.status(500).body(
                                new ApiResponse(
                                        false,
                                        "Failed to delete the already existing file"
                                )
                        );
                    }

                }

                // TODO: 5/27/2023  Finished the file upload

            }else {
                return ResponseEntity.status(500).body(
                        new ApiResponse(
                                false,
                                "The Instructor with id: " + instructorId + " does not exists"
                        )
                );
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ApiResponse(
                            false ,
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    public ResponseEntity<ApiResponse> createManyInstructor(List<CreateInstructorDTO> createInstructorDTOS) {
        try {
            List<Instructor> instructors = new ArrayList<Instructor>();
            int i = 0;
            while (i < createInstructorDTOS.size()){
                if (courseRepository.existsById(createInstructorDTOS.get(i).getCourseId())){
                    Course course = courseRepository.findById(createInstructorDTOS.get(i).getCourseId()).get();
                    Instructor instructor = new Instructor(
                            createInstructorDTOS.get(i).getFirstName(),
                            createInstructorDTOS.get(i).getLastName(),
                            createInstructorDTOS.get(i).getDateOfBirth(),
                            createInstructorDTOS.get(i).getPhoneNumber(),
                            createInstructorDTOS.get(i).getSalary(),
                            createInstructorDTOS.get(i).getRemunerationDate(),
                            course
                    );
                    instructors.add(instructor);
                    i++;
                }else {
                    return ResponseEntity.status(404).body(
                            new ApiResponse(
                                    false,
                                    "The course with id: " + createInstructorDTOS.get(i).getCourseId() + " does not exit"
                            )
                    );
                }
            }

            instructorRepository.saveAll(instructors);
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully created all the instructors",
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
