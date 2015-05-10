package com.vosmann.jis.exif;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class ExifController {

    private static final Logger LOG = LogManager.getLogger(ExifController.class);

    @Autowired
    private ExifService exifService;

    @RequestMapping(name = "exif", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> getExifById(@PathVariable final String id) {
        LOG.debug("Received exif GET by id request.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping(name = "exif", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity<String> searchExif(@PathVariable final String id) {
        LOG.debug("Received exif POST search by EXIF request.");
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @RequestMapping("/")
    @ResponseBody
    String test() {
        return "Exif service reporting in.";
    }

    public static void main(final String[] args) {
        SpringApplication.run(ExifController.class, args);
    }

}
