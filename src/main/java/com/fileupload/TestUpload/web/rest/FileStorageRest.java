package com.fileupload.TestUpload.web.rest;

import com.fileupload.TestUpload.demo.FileStorage;
import com.fileupload.TestUpload.server.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("api")
public class FileStorageRest {

    @Value("${upload.path}")
    private String uploadPath;
    private final FileStorageService storageService;

    public FileStorageRest(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("create")
    private ResponseEntity create(@RequestParam("file") MultipartFile multipartFile) {
        storageService.save(multipartFile);
        return ResponseEntity.ok("File " + multipartFile.getOriginalFilename() + " yuklandi");
    }

    @GetMapping("preview/{hashId}")
    private ResponseEntity previewFile(@PathVariable("hashId") String hashId) throws MalformedURLException {
        FileStorage fileStorage = storageService.findByHash(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; fileName=\"" + URLEncoder.encode(hashId)).contentType(MediaType.parseMediaType(fileStorage.getContentType())).contentLength(fileStorage.getSize()).body(new FileUrlResource(String.format("%s/%s", uploadPath, fileStorage.getUploadPath())));
    }

    @GetMapping("download/{hashId}")
    private ResponseEntity downloadFile(@PathVariable("hashId") String hashId) throws MalformedURLException {
        FileStorage fileStorage = storageService.findByHash(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=\"" + URLEncoder.encode(hashId)).contentType(MediaType.parseMediaType(fileStorage.getContentType())).contentLength(fileStorage.getSize()).body(new FileUrlResource(String.format("%s/%s", uploadPath, fileStorage.getUploadPath())));
    }

    @DeleteMapping("delete/{hashId}")
    private ResponseEntity deleteFile(@PathVariable("hashId") String hashId) throws MalformedURLException {
        storageService.removeByFileStorage(hashId);
        return ResponseEntity.ok("FIle o'chirildi");
    }


}
