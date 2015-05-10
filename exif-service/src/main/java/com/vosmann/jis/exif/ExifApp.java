package com.vosmann.jis.exif;

import com.vosmann.jis.aws.s3.Downloader;
import com.vosmann.jis.aws.s3.S3Downloader;
import com.vosmann.jis.aws.s3.S3Uploader;
import com.vosmann.jis.aws.s3.Uploader;
import com.vosmann.jis.config.context.S3Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(S3Config.class)
public class ExifApp {

    @Bean
    public ExifService photoService() {
        return new ExifService();
    }

    @Bean
    public Downloader downloader() {
        return new S3Downloader();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(3000);
        return factory;
    }


    public static void main(final String[] args) {
        SpringApplication.run(ExifApp.class);
    }


}
