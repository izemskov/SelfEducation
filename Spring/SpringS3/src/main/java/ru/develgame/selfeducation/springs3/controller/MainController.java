package ru.develgame.selfeducation.springs3.controller;

import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.develgame.selfeducation.springs3.dto.S3UploadedFileDtoResp;

import java.io.IOException;
import java.util.UUID;

@RestController
public class MainController {
    private final TransferManager transferManager;
    private final String bucketName;

    public MainController(@Autowired TransferManager transferManager,
                          @Value("${springs3.bucket.name}") String bucketName) {
        this.transferManager = transferManager;
        this.bucketName = bucketName;
    }

    @PostMapping("upload")
    public ResponseEntity<S3UploadedFileDtoResp> update(@RequestPart("file") MultipartFile file) throws IOException, InterruptedException {
        String key = UUID.randomUUID().toString();

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        // TODO - IOException, InterruptedException
        Upload upload = transferManager.upload(bucketName, key, file.getInputStream(), metadata);
        upload.waitForUploadResult();

        return ResponseEntity.ok(new S3UploadedFileDtoResp(key));
    }

}
