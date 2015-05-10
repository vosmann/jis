package com.vosmann.jis.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.vosmann.jis.config.props.S3Props;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class S3Downloader implements Downloader {

    private static final Logger LOG = LogManager.getLogger(S3Downloader.class);

    @Autowired
    private S3Props props;

    @Autowired
    private TransferManager transferManager;

    @Autowired
    private AmazonS3Client client;

    @Override
    public InputStream download(final String id) {

        final S3Address address = new S3Address.Builder().bucket(props.getBucket())
                                                         .keyPart(props.getKeyPrefix())
                                                         .keyPart(id) // No file extension.
                                                         .build();
        final S3Object s3Object = client.getObject(address.getBucket(), address.getKey());
        return s3Object.getObjectContent();
    }

}
