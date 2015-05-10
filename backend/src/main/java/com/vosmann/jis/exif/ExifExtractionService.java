package com.vosmann.jis.exif;

import java.io.InputStream;

public interface ExifExtractionService {
    String extract(InputStream stream);
}
