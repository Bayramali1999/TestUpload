package com.fileupload.TestUpload.web.rest;

import com.fileupload.TestUpload.server.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class FileStorageRest {


    private final FileStorageService storageService;

    public FileStorageRest(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("create")
    private ResponseEntity create(@RequestParam("file") MultipartFile multipartFile) {
        storageService.save(multipartFile);
        return ResponseEntity.ok("File " + multipartFile.getOriginalFilename() + " yuklandi");

    }


}
