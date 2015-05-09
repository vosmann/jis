package com.vosmann.jis.image;

import java.io.InputStream;

public interface ExifExtractionService {
    String extract(InputStream stream);
}
