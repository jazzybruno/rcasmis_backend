package com.jazzybruno.example.v1.serviceImpls;

import com.jazzybruno.example.v1.dto.requests.CreateStudentDTO;
import com.jazzybruno.example.v1.dto.requests.UpdateStudentDTO;
import com.jazzybruno.example.v1.dto.responses.FileUploadResponse;
import com.jazzybruno.example.v1.models.Student;
import com.jazzybruno.example.v1.payload.ApiResponse;
import com.jazzybruno.example.v1.repositories.StudentRepository;
import com.jazzybruno.example.v1.services.StudentService;
import com.jazzybruno.example.v1.utils.FileDownload;
import com.jazzybruno.example.v1.utils.FileUpload;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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
    public ResponseEntity<?> getStudentProfile(Long studentId) throws IOException {
       if (studentRepository.existsById(studentId)){
           Student student = studentRepository.findById(studentId).get();
           String fileCode = student.getProfileId();
           if(fileCode == null){
               return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                       new ApiResponse(
                               false,
                               "The student with id: " + studentId + " does not have a profile"
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
                           "The Student with id: " + studentId + " does not exist"
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

    @Override
    public ResponseEntity<ApiResponse> createManyStudents(List<CreateStudentDTO> createStudentDTOS) {
        try {
            List<Student> students = new ArrayList<Student>();
            int i = 0;
            while (i< createStudentDTOS.size()){
                Student student = new Student(
                        createStudentDTOS.get(i).getFirstName(),
                        createStudentDTOS.get(i).getLastName(),
                        createStudentDTOS.get(i).getDateOfBirth(),
                        createStudentDTOS.get(i).getEmail(),
                        createStudentDTOS.get(i).isInternational(),
                        createStudentDTOS.get(i).isPartTime(),
                        createStudentDTOS.get(i).isRepeating()
                );
                students.add(student);
                i++;
            }

            studentRepository.saveAll(students);
            return ResponseEntity.ok().body(
                    new ApiResponse(
                            true,
                            "Successfully created all the students",
                            students
                    )
            );

        }catch (Exception e){
            return ResponseEntity.status(500).body(
                    new ApiResponse(
                            true,
                            e.getMessage()
                    )
            );
        }
    }

    @Override
    @Transactional
    public ResponseEntity<ApiResponse> uploadStudentPhoto(Long studentId , MultipartFile multipartFile) {
        try {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            long size = multipartFile.getSize();
            if(studentRepository.existsById(studentId)){
                Student student = studentRepository.findById(studentId).get();
                if(student.getProfileId() == null){
                    String fileCode = FileUpload.saveFile(fileName , multipartFile);
                    FileUploadResponse fileUploadResponse = new FileUploadResponse();
                    fileUploadResponse.setSize(size);
                    fileUploadResponse.setFileName(fileName);
                    fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                    student.setProfileId(fileCode);

                    return ResponseEntity.ok().body(
                            new ApiResponse(
                                    true,
                                    "Successfully uploaded the student's picture with name: " + student.getFirstName(),
                                    fileUploadResponse
                            )
                    );

                }else{
                    // TODO: 5/27/2023 Delete the existing and add the new photo
                    String savedFileCode = student.getProfileId();
                    String filePath = FileUpload.getFile(savedFileCode);
                    System.out.println(filePath);
                    boolean isFilePresent = FileUpload.deleteFile(filePath);
                    if(isFilePresent){
                        String fileCode = FileUpload.saveFile(fileName , multipartFile);
                        FileUploadResponse fileUploadResponse = new FileUploadResponse();
                        fileUploadResponse.setSize(size);
                        fileUploadResponse.setFileName(fileName);
                        fileUploadResponse.setDownloadUri("/downloadFile/" + fileCode);
                        student.setProfileId(fileCode);

                        return ResponseEntity.ok().body(
                                new ApiResponse(
                                        true,
                                        "Successfully uploaded the student's picture with name: " + student.getFirstName(),
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
                                "The student with id: " + studentId + " does not exists"
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
