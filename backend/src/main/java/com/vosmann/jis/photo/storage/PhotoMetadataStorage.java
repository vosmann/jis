package com.vosmann.jis.photo.storage;

import com.vosmann.jis.photo.PhotoMetadata;

public interface PhotoMetadataStorage {

    void store(PhotoMetadata photoMetadata);

    void flagWithExif(String id);

    PhotoMetadata get(String id);

}
