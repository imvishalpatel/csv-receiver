package com.example.csvreceiver.service;

import org.springframework.web.multipart.MultipartFile;

public interface CsvStoreService {
    public void storeFile(MultipartFile file) throws Exception;
}
