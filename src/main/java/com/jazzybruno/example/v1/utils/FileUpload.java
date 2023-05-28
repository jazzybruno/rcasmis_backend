package com.jazzybruno.example.v1.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileUpload {
    private static String filePath = null;
    public static String saveFile(String fileName , MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get("Files-Upload");

        if(!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        String fileCode = RandomStringUtils.randomAlphanumeric(9);

        try(InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileCode + "-" + fileName);
            Files.copy(inputStream, filePath ,  StandardCopyOption.REPLACE_EXISTING);
        }catch (IOException exception){
            throw new IOException("Could not save file: " + fileName , exception);
        }
        return fileCode;
    }

    public static String getFile(String fileCode) throws IOException{
        Path dirPath = Paths.get("Files-Upload");
        Files.list(dirPath).forEach(file ->{
            if (file.getFileName().toString().startsWith(fileCode)){
                filePath = file.getFileName().toString();
            }
        });
        return filePath;
    }

    public static boolean deleteFile(String filePath){
          try {
              Path pathFileToDelete = Paths.get(filePath);
              if(Files.exists(pathFileToDelete)){
                  Files.delete(pathFileToDelete);
                  return true;
              }else {
                  return false;
              }
          }catch (IOException e){
              e.printStackTrace();
              return  false;
          }
    }
}
