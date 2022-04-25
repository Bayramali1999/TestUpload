package com.fileupload.TestUpload.repo;

import com.fileupload.TestUpload.demo.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileStorageRepo extends JpaRepository<FileStorage, Long> {
}
