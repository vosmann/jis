package com.vosmann.jis.photo;

public class FullPhotoMetadata {

    private String id;
    private String user;
    private String description;
    private String url;
    private ExifMetadataWithoutId ex;

    public static class ExifMetadataWithoutId {
        private final String dateTime;
        private final String exposureTime;
        private final String fNumber;
        private final String orientation;

        public ExifMetadataWithoutId(String dateTime, String exposureTime, String fNumber, String orientation) {
            this.dateTime = dateTime;
            this.exposureTime = exposureTime;
            this.fNumber = fNumber;
            this.orientation = orientation;
        }
    }

}
