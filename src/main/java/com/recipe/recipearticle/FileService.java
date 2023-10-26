package com.recipe.recipearticle;

import static com.recipe.recipearticle.Dto.Response.ResponseStatus.IMAGE_UPLOAD_FAIL;

import com.recipe.recipearticle.Exception.BaseException;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    @Value("${upload.image.location}")
    private String location;

    @PostConstruct
    void postConstruct() {
        File dir = new File(location);
        if (!dir.exists()) {
            dir.mkdir();
        }
    }

    public void upload(MultipartFile file, String filename) {
        try {
            file.transferTo(new File(location + filename));
        } catch (IOException e) {
            throw new BaseException(IMAGE_UPLOAD_FAIL);
        }
    }

    public ResponseEntity<Resource> read(String uniqueName) {
        Resource resource = new FileSystemResource(location + uniqueName);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try {
            filePath = Paths.get(location);
            header.add("Content-Type", Files.probeContentType(filePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

    public void delete(String filename) {
        new File(location + filename).delete();
    }
}