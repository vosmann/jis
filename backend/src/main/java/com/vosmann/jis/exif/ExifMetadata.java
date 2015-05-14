package com.vosmann.jis.exif;

// Specification expects all fields as strings.
public class ExifMetadata {

    private String dateTime;
    private String exposureTime;
    private String fNumber;
    private String orientation;

    private ExifMetadata(Builder builder) {
        dateTime = builder.dateTime;
        exposureTime = builder.exposureTime;
        fNumber = builder.fNumber;
        orientation = builder.orientation;
    }

    public static final class Builder {
        private String dateTime;
        private String exposureTime;
        private String fNumber;
        private String orientation;

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
}
