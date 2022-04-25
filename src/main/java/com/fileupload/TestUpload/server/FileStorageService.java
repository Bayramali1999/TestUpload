package com.fileupload.TestUpload.server;

import com.fileupload.TestUpload.demo.FIleStorageType;
import com.fileupload.TestUpload.demo.FileStorage;
import com.fileupload.TestUpload.repo.FileStorageRepo;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {

    @Value("${upload.path}")
    private String fileUploadPath;

    private final Hashids hashids;
    private final FileStorageRepo repo;

    public FileStorageService(FileStorageRepo repo) {
        this.repo = repo;
        hashids = new Hashids(getClass().getName(), 6);

    }

    public void save(MultipartFile multipartFile) {
        FileStorage fileStorage = new FileStorage();

        fileStorage.setName(multipartFile.getOriginalFilename());
        fileStorage.setSize(multipartFile.getSize());
        fileStorage.setContentType(multipartFile.getContentType());
        fileStorage.setfIleStorageType(FIleStorageType.ACTIVE);
        fileStorage.setFileExtension(getExt(multipartFile.getOriginalFilename()));
        repo.save(fileStorage);
        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_path/%d/%d/%d", fileUploadPath, now.getYear() + 1900, now.getMonth() + 1, now.getDay()));

        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("File yaratildi");
        }

        fileStorage.setHashId(hashids.encode(fileStorage.getId()));
        fileStorage.setUploadPath(String.format("%s/upload_path/%d/%d/%d/%s/%s", fileUploadPath, now.getYear() + 1900, now.getMonth() + 1, now.getDay(), fileStorage.getHashId(), fileStorage.getFileExtension()));
        repo.save(fileStorage);

        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder, String.format("%s.%s", fileStorage.getHashId(), fileStorage.getFileExtension()));
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileStorage> findAll() {
        return repo.findAll();
    }

    private String getExt(String path) {
        String ext = null;
        if (path != null && !path.isEmpty()) {
            int dot = path.lastIndexOf(".");
            if (dot > 0 && dot <= path.length() - 2) {
                ext = path.substring(dot);
            }
        }
        return ext;
    }
}
