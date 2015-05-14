package com.vosmann.jis.photo;

import com.vosmann.jis.aws.s3.Uploader;
import com.vosmann.jis.photo.storage.PhotoMetadataStorage;
import com.vosmann.jis.queue.IdConsumer;
import com.vosmann.jis.queue.IdSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.InputStream;
import java.util.Optional;

public class PhotoService implements IdConsumer {

    private static final Logger LOG = LogManager.getLogger(PhotoService.class);

    @Autowired
    private Uploader uploader;
    @Autowired
    private IdSender idSender;
    @Autowired
    private PhotoMetadataStorage storage;

    public String process(final String userMetadata, final InputStream stream) {

        final PhotoMetadata.Builder metadataBuilder = PhotoMetadata.Builder.of(userMetadata, userMetadata); // TODO

        final Optional<String> url = uploader.upload(metadataBuilder.getId(), stream);

        if (url.isPresent()) {
            metadataBuilder.url(url.get());
            final PhotoMetadata photoMetadata = metadataBuilder.build();
            storage.store(photoMetadata);
            idSender.send(photoMetadata.getId());
        } else {
            LOG.error("File upload failed.");
        }

        return "Failed";
    }

    @Override
    public void accept(final String id) {
        LOG.error("Exif data was extracted from photo with ID {}.", id);
        storage.flagWithExif(id);
    }

}
