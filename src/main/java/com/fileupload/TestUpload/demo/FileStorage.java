package com.fileupload.TestUpload.demo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FileStorage implements Serializable {
    @SequenceGenerator(name = "file_seq", sequenceName = "file_seq", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_seq")
    private Long id;
    private String name;
    private String contentType;
    private Long size;
    @Enumerated(EnumType.STRING)
    private FIleStorageType fIleStorageType;
    private String uploadPath;
    private String fileExtension;
    private String hashId;

    public String getHashId() {
        return hashId;
    }

    public void setHashId(String hashId) {
        this.hashId = hashId;
    }

    public FileStorage() {
    }

    public String getFileExtension() {
        return fileExtension;
    }

    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public FIleStorageType getfIleStorageType() {
        return fIleStorageType;
    }

    public void setfIleStorageType(FIleStorageType fIleStorageType) {
        this.fIleStorageType = fIleStorageType;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}
