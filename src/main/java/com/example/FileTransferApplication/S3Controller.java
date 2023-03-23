package com.example.FileTransferApplication;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CopyObjectRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
public class S3Controller {

//    private final AmazonS3 s3 = AmazonS3ClientBuilder.standard()
//            .withEndpointConfiguration(LocalStackEndpointConfiguration.builder()
//                    .withEndpointUri("http://localhost:4566")
//                    .build())
//            .withCredentials(LocalStackContainer.getInstance().getLocalstack().getDefaultCredentialsProvider())
//            .build();

    AmazonS3 s3 = AmazonS3ClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials("accessKeyId", "secretAccessKey")))
            .withRegion(Regions.US_EAST_1)
            .build();

    @PostMapping("/transfer-files")
    public ResponseEntity<String> transferFiles(@RequestBody TransferRequest transferRequest) {

        String sourceFolder = transferRequest.getSourceFolder();
        String destinationFolder = transferRequest.getDestinationFolder();

        ObjectListing objectListing = s3.listObjects(transferRequest.getBucketName(), sourceFolder);
        List<S3ObjectSummary> objectSummaries = objectListing.getObjectSummaries();

        for (S3ObjectSummary objectSummary : objectSummaries) {
            String objectKey = objectSummary.getKey();
            String destinationKey = objectKey.replace(sourceFolder, destinationFolder);

            CopyObjectRequest copyObjectRequest = new CopyObjectRequest(
                    transferRequest.getBucketName(), objectKey,
                    transferRequest.getBucketName(), destinationKey);
            s3.copyObject(copyObjectRequest);

            s3.deleteObject(transferRequest.getBucketName(), objectKey);
        }
        return new ResponseEntity<>("Files transferred successfully!", HttpStatus.OK);
    }
}
