package com.vosmann.jis.exif.storage;

import com.vosmann.jis.exif.ExifMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class ExifMetadataSolrStorage implements ExifMetadataStorage {

    private static final Logger LOG = LogManager.getLogger(ExifMetadataSolrStorage.class);

    // Into own enum.
    private static final String ID = "id";
    private static final String DATE_TIME = "dateTime";
    private static final String F_NUMBER = "fNumber";
    private static final String EXPOSURE_TIME = "exposureTime";
    private static final String ORIENTATION = "orientation";

    @Autowired
    private SolrServer solr;

    @Override
    public void store(ExifMetadata exifMetadata) {
        final SolrInputDocument doc = toDoc(exifMetadata);
        add(doc);
    }

    @Override
    public Optional<ExifMetadata> get(String id) {
        return null;
    }

    @Override
    public List<ExifMetadata> search(ExifMetadata query) {
        return null;
    }

    private SolrInputDocument toDoc(final ExifMetadata exifMetadata) {
        final SolrInputDocument doc = new SolrInputDocument();
        doc.addField(ID, exifMetadata.getId());
        doc.addField(DATE_TIME, exifMetadata.getDateTime());
        doc.addField(F_NUMBER, exifMetadata.getfNumber());
        doc.addField(EXPOSURE_TIME, exifMetadata.getExposureTime());
        doc.addField(ORIENTATION, exifMetadata.getOrientation());
        return doc;
    }

    private void add(final SolrInputDocument doc) {
        solr.
        try {
            solr.add(doc);
            solr.commit();
        } catch (final SolrServerException | IOException | RuntimeException e) {
            LOG.error("Problem while adding new document.", e);
        }
    }

}
