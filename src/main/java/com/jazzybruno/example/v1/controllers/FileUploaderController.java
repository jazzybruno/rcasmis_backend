package com.jazzybruno.example.v1.controllers;

import com.jazzybruno.example.v1.dto.responses.FileUploadResponse;
import com.jazzybruno.example.v1.utils.FileDownload;
import com.jazzybruno.example.v1.utils.FileUpload;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/files")
public class FileUploaderController  {

    @GetMapping("/download/{fileCode}")
    public ResponseEntity<?> downloadFile(@PathVariable("fileCode") String fileCode){
        FileDownload fileDownload = new FileDownload();
        Resource resource = null;
        try {
         resource = fileDownload.getFileAsResource(fileCode);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
        if (resource == null){
            return new ResponseEntity<>("File not found" , HttpStatus.NOT_FOUND);
        }
        String contentType = "application/octet-stream";
        String headerValue = "attachment; filename=\"" + resource.getFilename() +"\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, headerValue)
                .body(resource);
    }

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
