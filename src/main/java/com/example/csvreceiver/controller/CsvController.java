package com.example.csvreceiver.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.csvreceiver.service.CsvStoreService;

@RestController
public class CsvController {

    @Autowired
    CsvStoreService service;

    Logger logger = LoggerFactory.getLogger(CsvController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        UploadFileResponse response = new UploadFileResponse();

        try {
            service.storeFile(file);
            response.setSuccess(true);
        } catch (Exception e1) {
            logger.info("Exception while storing file. e={}", e1);
            response.setSuccess(false);
            response.setErrorMsg(e1.getMessage());
        }
        response.setRealName(file.getOriginalFilename());

        return response;
    }
}
