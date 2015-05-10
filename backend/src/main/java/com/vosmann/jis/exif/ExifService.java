package com.vosmann.jis.exif;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

public class ExifService {

    private static final Logger LOG = LogManager.getLogger(ExifService.class);

    public String process(final String id) {

        try (final Stream stream = null) {
            final Metadata metadata = ImageMetadataReader.readMetadata(stream);
            return metadata.toString();
        } catch (final ImageProcessingException | IOException e) {
            LOG.error("Image processing problem.", e);
            return "Failure";
        }
    }

}
