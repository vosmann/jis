package com.vosmann.jis.aws.s3;

import java.io.InputStream;
import java.util.Optional;

public interface Uploader {

    Optional<String> upload(String address, InputStream stream);

}
