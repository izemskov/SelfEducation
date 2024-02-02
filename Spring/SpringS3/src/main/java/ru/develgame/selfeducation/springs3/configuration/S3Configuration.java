package ru.develgame.selfeducation.springs3.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

@Configuration
public class S3Configuration {
    @Bean
    public AmazonS3 amazonS3Client(@Value("${storage.s3.accessKey:key}") String accessKey,
                                   @Value("${storage.s3.secretKey:secret}") String secretKey,
                                   @Value("${storage.s3.endpoint:localhost}") String endpoint,
                                   @Value("${storage.s3.region:region}") String region) {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpoint, region))
                .withPathStyleAccessEnabled(true)
                .build();
    }

    @Bean
    public TransferManager transferManager(@Autowired AmazonS3 amazonS3,
                                           @Value("${storage.s3.transferManager.uploadThreads:10}") int maxUploadThreads,
                                           @Value("${storage.s3.transferManager.uploadThreshold:200}") int uploadThreshold) {
        int MB = 1024 * 1024;
        return TransferManagerBuilder.standard()
                .withS3Client(amazonS3)
                .withMultipartUploadThreshold((long) uploadThreshold * MB)
                .withExecutorFactory(() -> Executors.newFixedThreadPool(maxUploadThreads))
                .build();
    }
}
