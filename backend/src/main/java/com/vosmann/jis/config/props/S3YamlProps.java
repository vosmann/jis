package com.vosmann.jis.config.props;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Returns properties provided in a YAML file, i.e. properties not changeable during run time.
 */
@ConfigurationProperties(prefix="s3")
@EnableConfigurationProperties
public class S3YamlProps implements S3Props {

    private String bucket;
    private String keyPrefix;
    private String accessKey;
    private String secretKey;

    @Override
    public String getBucket() {
        return bucket;
    }

    @Override
    public String getKeyPrefix() {
        return keyPrefix;
    }

    @Override
    public String getAccessKey() {
        return accessKey;
    }

    @Override
    public String getSecretKey() {
        return secretKey;
    }

    // Setters neccessary for Spring's YAML property file reading.
    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
