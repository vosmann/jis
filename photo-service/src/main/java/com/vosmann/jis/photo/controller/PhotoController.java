package com.vosmann.jis.photo.controller;

import com.vosmann.jis.image.ExifExtractionService;
import com.vosmann.jis.image.ExifExtractionServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@Controller
@EnableAutoConfiguration
public class PhotoController {

    private static final Logger LOG = LogManager.getLogger(PhotoController.class);
    private static final ExifExtractionService EXTRACTION_SERVICE = new ExifExtractionServiceImpl(); // todo

    // @RequestPart("meta") final String metadata
    @RequestMapping(name = "photo", method = POST)
    @ResponseBody
    String photo(@RequestPart("metadata") final String userMetadata, @RequestPart("image") final MultipartFile imageFile) {
        final StringBuilder response = new StringBuilder("Metadata: ");
        response.append(userMetadata);
        try {
            final InputStream stream = imageFile.getInputStream();
            final String extractedMetadata = EXTRACTION_SERVICE.extract(stream);
            response.append("Extracted metadata: ").append(extractedMetadata);
        } catch (final IOException e) {
            LOG.error("Could not extract metadata.", e);
        }

        return response.toString();
    }

    @RequestMapping("/test")
    @ResponseBody
    String test() {
        return "(test) Photo service reporting in.";
    }

    public static void main(final String[] args) {
        SpringApplication.run(PhotoController.class, args);
    }

}
