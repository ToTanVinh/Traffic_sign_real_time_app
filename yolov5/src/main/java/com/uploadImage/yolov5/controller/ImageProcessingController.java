package com.uploadImage.yolov5.controller;


import com.uploadImage.yolov5.util.MultipartInputStreamFileResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ImageProcessingController {
    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/upload")
    public ResponseEntity<byte[]> handleFileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fastApiUrl = "http://localhost:8000/predict/";

        // Sử dụng RestTemplate để tạo request gửi file
        RestTemplate restTemplate = new RestTemplate();

        // Tạo đối tượng HttpHeaders với kiểu multipart/form-data
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        // Đóng gói file vào HttpEntity
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        // Gửi yêu cầu POST đến FastAPI và nhận kết quả
        ResponseEntity<byte[]> response = restTemplate.postForEntity(fastApiUrl, requestEntity, byte[].class);

        // Trả về phản hồi là hình ảnh đã xử lý từ FastAPI
        return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
    }
}
