package com.epartner.services;

import com.epartner.exceptions.StorageException;
import com.epartner.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * Created by maty on 4/9/16.
 */
@Service
public class StorageService {

    private final Path rootLocation;

    @Autowired
    public StorageService(@Value("${epartner.images.path}") String imagePath) {
        this.rootLocation = Paths.get(imagePath);
    }

    public String store(MultipartFile file) {
        String fileName = UUID.randomUUID().toString() + getExtensionFile(file);
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file " + file.getOriginalFilename());
            }
            Files.copy(file.getInputStream(), this.rootLocation.resolve(fileName));
        } catch (IOException e) {
            throw new StorageException("Failed to store file " + file.getOriginalFilename(), e);
        }
        return fileName;
    }

    private String getExtensionFile(MultipartFile file) {
        return "." + file.getOriginalFilename().split("\\.")[1];
    }

    public void delete(String fileName) {
        try {
            Files.delete(this.rootLocation.resolve(fileName));
        } catch (IOException e) {

            throw new StorageFileNotFoundException("Could not read file: " + fileName, e);
        }
    }
}