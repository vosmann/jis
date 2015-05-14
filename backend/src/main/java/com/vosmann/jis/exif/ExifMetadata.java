package com.vosmann.jis.exif;

import com.google.common.base.Preconditions;

// Specification mentions all fields as strings.
public class ExifMetadata {

    private final String id;
    private final String dateTime;
    private final String exposureTime;
    private final String fNumber;
    private final String orientation;

    private ExifMetadata(Builder builder) {
        id = builder.id;
        dateTime = builder.dateTime;
        exposureTime = builder.exposureTime;
        fNumber = builder.fNumber;
        orientation = builder.orientation;
    }

    public static final class Builder {
        private String id;
        private String dateTime;
        private String exposureTime;
        private String fNumber;
        private String orientation;

        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder dateTime(String dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Builder exposureTime(String exposureTime) {
            this.exposureTime = exposureTime;
            return this;
        }

        public Builder fNumber(String fNumber) {
            this.fNumber = fNumber;
            return this;
        }

        public Builder orientation(String orientation) {
            this.orientation = orientation;
            return this;
        }

        public ExifMetadata build() {
            // Could warn if part is missing, error if everything is missing.
            return new ExifMetadata(this);
        }
    }

    public String getId() {
        return id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public String getExposureTime() {
        return exposureTime;
    }

    public String getfNumber() {
        return fNumber;
    }

    public String getOrientation() {
        return orientation;
    }

}
