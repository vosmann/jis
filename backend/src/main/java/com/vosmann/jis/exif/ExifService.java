package com.vosmann.jis.exif;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.vosmann.jis.aws.s3.Downloader;
import com.vosmann.jis.exif.storage.ExifMetadataStorage;
import com.vosmann.jis.queue.IdConsumer;
import com.vosmann.jis.queue.IdSender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class ExifService implements IdConsumer {

    private static final Logger LOG = LogManager.getLogger(ExifService.class);

    private static final String GPS_TIMESTAMP_TAG_NAME = "GPS Time-Stamp";
    private static final String F_NUMBER_TAG_NAME = "F-Number";
    private static final String EXPOSURE_TIME_TAG_NAME = "Exposure Time";
    private static final String ORIENTATION_TAG_NAME = "Orientation";

    @Autowired
    private Downloader downloader;
    @Autowired
    private ExifMetadataStorage storage;
    @Autowired
    private IdSender idSender;

    private Optional<ExifMetadata> getExifMetadata(final String id) {
        return Optional.empty();
    }

    @Override
    public void accept(final String id) {
         process(id);
    }

    public Optional<String> process(final String id) {

        final Optional<Metadata> metadata = extractAllMetadata(id);
        if (metadata.isPresent()) {
            final ExifMetadata exifMetadata = toExifMetadata(id, metadata.get());
            storage.store(exifMetadata);
            idSender.send(id);
        }

        return Optional.empty();
    }

    private Optional<Metadata> extractAllMetadata(final String id) {
        try (final InputStream stream = downloader.download(id)) {
            final Metadata metadata = ImageMetadataReader.readMetadata(stream);
            return Optional.of(metadata);
        }  catch (final IOException e) {
            LOG.error("File download problem.", e);
        } catch (final ImageProcessingException e) {
            LOG.error("File processing problem.", e);
        }

        return Optional.empty();
    }

    /**
     * May return empty ExifMetadata.
     */
    private ExifMetadata toExifMetadata(final String id, final Metadata metadata) {
        ExifMetadata.Builder builder = new ExifMetadata.Builder();
        builder.id(id);

        for (final Directory directory : metadata.getDirectories()) {
            for (final Tag tag : directory.getTags()) {

                final String name = tag.getTagName();
                final String description = tag.getDescription();
                LOG.error("Name: {}, description: {}", name, description);

                if (GPS_TIMESTAMP_TAG_NAME.equalsIgnoreCase(name)) {
                    builder.dateTime(description);
                } else if (EXPOSURE_TIME_TAG_NAME.equalsIgnoreCase(name)) {
                    builder.exposureTime(description);
                } else if (F_NUMBER_TAG_NAME.equalsIgnoreCase(name)) {
                    builder.fNumber(description);
                } else if (ORIENTATION_TAG_NAME.equalsIgnoreCase(name)) {
                    builder.orientation(description);
                }
            }
        }

        return builder.build();
    }

}
