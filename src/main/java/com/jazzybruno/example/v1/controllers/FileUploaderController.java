package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.responses.FileUploadResponse;
import com.jazzybruno.example.v1.utils.FileUpload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploaderController  {

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file")MultipartFile multipartFile) throws IOException {
     String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
     long size = multipartFile.getSize();
     String filecode = FileUpload.saveFile(fileName, multipartFile);
     FileUploadResponse fileUploadResponse = new FileUploadResponse();
     fileUploadResponse.setFileName(fileName);
     fileUploadResponse.setDownloadUri("/downloadFile/" + filecode);
     fileUploadResponse.setSize(size);
     return new ResponseEntity<>(fileUploadResponse , HttpStatus.OK);
    }
}
