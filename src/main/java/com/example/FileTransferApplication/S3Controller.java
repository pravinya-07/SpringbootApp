package com.example.FileTransferApplication;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class S3Controller {

    @Autowired
    @Qualifier("amazons3")
    AmazonS3 amazonS3;
    @PostMapping("/transfer")
    public ResponseEntity<String> transferFiles(@RequestBody TransferRequest transferRequest) {

        System.out.println(amazonS3.listObjects("uploads"));
        return new ResponseEntity<>("Files transferred successfully!", HttpStatus.OK);
    }
}