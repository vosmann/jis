package com.vosmann.jis.exif;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@EnableAutoConfiguration
public class ExifController {

    private static final Logger LOG = LogManager.getLogger(ExifController.class);

    @Autowired
    private ExifService exifService;

    // This won't be in the final solution; extraction will be triggered by queue.
    @RequestMapping(value = "exif/{id}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> extract(@PathVariable final String id) {
        LOG.debug("Received exif GET by id request.");
        final Optional<String> exifMetadata = exifService.process(id);
        if (exifMetadata.isPresent()) {
            return new ResponseEntity<>(exifMetadata.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @RequestMapping(name = "exif", method = RequestMethod.GET)
//    @ResponseBody
//    ResponseEntity<String> get(@PathVariable final String id) {
//        LOG.debug("Received exif POST get by EXIF request.");
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @RequestMapping(name = "exif", method = RequestMethod.POST)
//    @ResponseBody
//    ResponseEntity<String> search(@PathVariable final String id) {
//        LOG.debug("Received exif POST search by EXIF request.");
//        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    public static void main(final String[] args) {
        SpringApplication.run(ExifController.class, args);
    }

}
