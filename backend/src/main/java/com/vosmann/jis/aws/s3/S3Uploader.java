package com.vosmann.jis.aws.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.vosmann.jis.config.props.S3Props;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class S3Uploader implements Uploader {

    private static final Logger LOG = LogManager.getLogger(S3Uploader.class);

    @Autowired
    private S3Props props;

    @Autowired
    private TransferManager transferManager; // Allows uploads without knowing file size.

    /**
     * Parts of this implementation (upload(), close()) could be implemented in an abstract class.
     * @param id
     * @param stream
     * @return If the upload succeeded, the URL the file was uploaded to, absent otherwise.
     */
    @Override
    public Optional<String> upload(final String id, InputStream stream) {

        Optional<String> url = Optional.empty();

        final S3Address address = new S3Address.Builder().bucket(props.getBucket())
                                                         .keyPart(props.getKeyPrefix())
                                                         .keyPart(id) // No file extension.
                                                         .build();

        final Upload upload = transferManager.upload(address.getBucket(), address.getKey(), stream, new ObjectMetadata());

        try {
            upload.waitForCompletion();
            url = Optional.of(address.getUrl());
        } catch (final AmazonClientException | InterruptedException e) {
            LOG.error("Could not upload file {}.", id, e);
        }

        return url;
    }

    private void close(final InputStream stream) {
        try {
            stream.close();
        } catch (IOException e) {
            LOG.error("Couldn't close file input stream.");
        }
    }

}
