package com.example.FileTransferApplication;
public class TransferRequest {

    private String bucketName;
    private String sourceFolder;
    private String destinationFolder;


    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getSourceFolder() {
        return sourceFolder;
    }

    public String getDestinationFolder() {
        return destinationFolder;
    }



    public void setSourceFolder(String sourceFolder) {
        this.sourceFolder = sourceFolder;
    }

    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }
}