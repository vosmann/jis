package com.vosmann.jis.aws.s3;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Stream;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Strings.*;
import static java.util.stream.Collectors.joining;

public class S3Address {

    private static final String BASIC_URI = "http://s3.amazonaws.com";
    private static final String DELIMITER = "/";

    private final String bucket;
    private final String key;
    private final String url;

    private S3Address(Builder builder) {
        bucket = builder.bucket;
        key = builder.keyParts.stream().collect(joining(DELIMITER)); // Java 8 for fun.
        url = Stream.of(BASIC_URI, bucket, key).collect(joining(DELIMITER));
    }

    public static final class Builder {
        private String bucket;
        private List<String> keyParts = Lists.newArrayList();

        public Builder bucket(String bucket) {
            checkArgument(!isNullOrEmpty(bucket), "Can't set a null/blank bucket.");
            this.bucket = bucket;
            return this;
        }

        public Builder keyPart(String keyPart) {
            checkArgument(!isNullOrEmpty(keyPart), "Can't add a null/blank key part.");
            this.keyParts.add(keyPart);
            return this;
        }

        public S3Address build() {
            return new S3Address(this);
        }
    }

    public String getBucket() {
        return bucket;
    }

    public String getKey() {
        return key;
    }

    public String getUrl() {
        return url;
    }

}
