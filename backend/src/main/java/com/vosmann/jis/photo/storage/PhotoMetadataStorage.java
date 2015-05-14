package com.vosmann.jis.photo.storage;

import com.vosmann.jis.photo.PhotoMetadata;

import java.util.Optional;

public interface PhotoMetadataStorage {

    void store(PhotoMetadata photoMetadata);

    void flagWithExif(String id);

    Optional<PhotoMetadata> get(String id);

}
