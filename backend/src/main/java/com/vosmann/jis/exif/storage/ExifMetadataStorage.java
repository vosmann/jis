package com.vosmann.jis.exif.storage;

import com.vosmann.jis.exif.ExifMetadata;
import com.vosmann.jis.photo.PhotoMetadata;

import java.util.List;
import java.util.Optional;

public interface ExifMetadataStorage {

    void store(ExifMetadata exifMetadata);

    Optional<ExifMetadata> get(String id);

    List<ExifMetadata> search(ExifMetadata query);

}
