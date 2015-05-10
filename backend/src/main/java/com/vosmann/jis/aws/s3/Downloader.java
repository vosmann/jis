package com.vosmann.jis.aws.s3;

import java.io.InputStream;
import java.util.Optional;

public interface Downloader {

    InputStream download(String id);

}
