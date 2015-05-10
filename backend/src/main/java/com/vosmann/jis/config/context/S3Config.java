package com.vosmann.jis.config.context;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.vosmann.jis.config.props.S3Props;
import com.vosmann.jis.config.props.S3YamlProps;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class S3Config {

    @Bean
    public S3Props s3Props() {
       return new S3YamlProps();
    }

    @Bean
    public AWSCredentials awsCredentials(final S3Props props) {
        final AWSCredentials credentials = new BasicAWSCredentials(props.getAccessKey(), props.getSecretKey());
        return credentials;
    }

    @Bean
    public AmazonS3Client amazonS3Client(final AWSCredentials credentials) {
        final AmazonS3Client client = new AmazonS3Client(credentials);
        // client.setRegion(Region.getRegion(Regions.EU_CENTRAL_1));
        return client;
    }

    @Bean
    public TransferManager transferManager(final AWSCredentials credentials) {
        return new TransferManager(credentials);
    }

}
