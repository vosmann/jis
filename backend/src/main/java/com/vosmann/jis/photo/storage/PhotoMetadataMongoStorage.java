package com.vosmann.jis.photo.storage;

import com.vosmann.jis.photo.PhotoMetadata;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;

public class PhotoMetadataMongoStorage implements PhotoMetadataStorage {

    private static final Logger LOG = LogManager.getLogger(PhotoMetadataMongoStorage.class);

    private static final String COLLECTION_NAME = "photoMetadata";

    private MongoTemplate mongo;

    public PhotoMetadataMongoStorage(final MongoTemplate mongo) {
        this.mongo = mongo;
    }

    @Override
    public void store(final PhotoMetadata photoMetadata) {
        storeSafely(photoMetadata);
    }

    @Override
    public void flagWithExif(final String id) {
        final PhotoMetadata photoMetadata = getSafely(id);
        final PhotoMetadata flagged = PhotoMetadata.copyWithExif(photoMetadata);
        updateSafely(flagged);
    }

    @Override
    public PhotoMetadata get(String id) {
        final PhotoMetadata photoMetadata = getSafely(id);
        return photoMetadata;
    }

    private void storeSafely(final PhotoMetadata photoMetadata) {
        try {
            mongo.insert(photoMetadata, COLLECTION_NAME);
        } catch (final RuntimeException e) {
            LOG.error("Problem while storing metadata with id {}.", photoMetadata.getId(), e);
        }
    }

    private void updateSafely(final PhotoMetadata photoMetadata) {
        try {
            mongo.save(photoMetadata, COLLECTION_NAME);
        } catch (final RuntimeException e) {
            LOG.error("Problem while storing metadata with id {}.", photoMetadata.getId(), e);
        }
    }

    private PhotoMetadata getSafely(final String id) {
        try {
            final PhotoMetadata photoMetadata = mongo.findById(id, PhotoMetadata.class, COLLECTION_NAME);
            return photoMetadata;
        } catch (final RuntimeException e) {
            LOG.error("Problem while getting metadata by id {}.", id, e);
            return null;
        }
    }

}
