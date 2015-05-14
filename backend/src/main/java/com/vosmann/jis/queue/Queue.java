package com.vosmann.jis.queue;

import com.google.common.base.Preconditions;

public enum Queue {

    PHOTO_UPLOAD("photo-upload"),
    EXIF_EXTRACTION("exif-extraction");

    private final String name;

    private Queue(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
