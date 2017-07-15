package com.example.csvreceiver.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvStoreServiceImpl implements CsvStoreService {

    Logger logger = LoggerFactory.getLogger(CsvStoreService.class);

    @Value("${csv.receiver.allowed.file.extensions}")
    String allowedExtensions;

    @Value("${csv.receiver.filedb.path}")
    String filePath;

    @Override
    public void storeFile(MultipartFile file) throws Exception {
        checkFileExtension(file);

        File destination = getFile(file);
        try {
            org.apache.commons.io.FileUtils.writeByteArrayToFile(destination, file.getBytes());
        } catch (IOException e) {
            logger.info("Problem in writting file to disk.");
            logger.info("Ecception: {}", e);
            throw new Exception("Problem in writting file to disk.");
        }

        System.out.println("File named " + file.getOriginalFilename() + " stored successfully");
    }

    private void checkFileExtension(MultipartFile file) throws Exception {

        String[] extensions = allowedExtensions.split(",");
        Set<String> allowedExtSet = new HashSet<String>(Arrays.asList(extensions));

        if (file == null) {
            throw new Exception("File is Empty");
        }

        String fileExt = file.getOriginalFilename().substring(
                file.getOriginalFilename().lastIndexOf("."));

        if (!allowedExtSet.contains(fileExt)) {
            throw new Exception("File extension must be .CSV");
        }
    }

    private File getFile(MultipartFile file) {
        return new File(filePath + "/" + file.getOriginalFilename());
    }
}
