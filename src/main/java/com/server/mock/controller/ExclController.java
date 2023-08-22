package com.server.mock.controller;

import com.server.mock.Service.impl.ExclServiceForUserData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/v1")
public class ExclController {
    @Autowired
    private ExclServiceForUserData exclServiceForUserData;


    @GetMapping("/excl/userData/{searchTerm}")
    public ResponseEntity<InputStreamResource> downloadUserInfo(@PathVariable String searchTerm) throws IOException {
        String fileName = "userData.xlsx";
        ByteArrayInputStream actualData = exclServiceForUserData.getUserData(searchTerm);
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<InputStreamResource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

        return body;
    }

    @GetMapping("/excl/attemptData/{userId}")
    public ResponseEntity<InputStreamResource> downloadUserQuizAttemptData(@PathVariable Long userId) throws IOException {
        String fileName = "attemptData.xlsx";
        ByteArrayInputStream actualData = exclServiceForUserData.getUserQuizAttemptData(userId);
        InputStreamResource file = new InputStreamResource(actualData);

        ResponseEntity<InputStreamResource> body = ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);

        return body;
    }
}
