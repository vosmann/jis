package com.vosmann.jis.photo;

import com.vosmann.jis.aws.s3.S3Uploader;
import com.vosmann.jis.aws.s3.Uploader;
import com.vosmann.jis.config.context.S3Config;
import com.vosmann.jis.config.props.S3Props;
import com.vosmann.jis.config.props.S3YamlProps;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(S3Config.class)
public class PhotoApp {

    @Bean
    public PhotoService photoService() {
        return new PhotoService();
    }

    @Bean
    public Uploader uploader() {
        return new S3Uploader();
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(10000);
        return factory;
    }


    public static void main(final String[] args) {
        SpringApplication.run(PhotoApp.class);
    }


}
