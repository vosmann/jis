package com.vosmann.jis.photo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

import static org.springframework.web.bind.annotation.RequestMethod.*;

@RestController
public class PhotoController {

    private static final Logger LOG = LogManager.getLogger(PhotoController.class);

    @Autowired
    private PhotoService photoService;

    @RequestMapping(name = "photo", method = POST)
    @ResponseBody
    ResponseEntity<String> photo(@RequestPart("metadata") final String userMetadata,
                                 @RequestPart("image") final MultipartFile file) {
        LOG.debug("Received photo POST request.");

        try (final InputStream stream = file.getInputStream()) {
            final String photoMetadata = photoService.process(userMetadata, stream);
            return new ResponseEntity<>(photoMetadata, HttpStatus.OK);
        } catch (final IOException e) {
            LOG.error("Could not process file upload stream.", e);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
