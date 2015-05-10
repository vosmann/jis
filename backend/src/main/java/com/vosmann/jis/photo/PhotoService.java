package com.vosmann.jis.photo;

import com.vosmann.jis.PhotoMetadata;
import com.vosmann.jis.aws.s3.Uploader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Optional;

public class PhotoService {

    private static final Logger LOG = LogManager.getLogger(PhotoService.class);

    @Autowired
    private Uploader uploader;

    public String process(final String userMetadata, final InputStream stream) {

        final PhotoMetadata.Builder metadataBuilder = PhotoMetadata.Builder.of(userMetadata, userMetadata); // TODO

        final Optional<String> url = uploader.upload(metadataBuilder.getId(), stream);

        if (url.isPresent()) {
            metadataBuilder.url(url.get());
            final PhotoMetadata metadata = metadataBuilder.build();
            store(metadata);
        } else {
            LOG.error("File upload failed.");
        }


        return "Failed";
    }

    private void store(PhotoMetadata metadata) {
        // todo
    }

}
