package com.vosmann.jis.photo;

import com.vosmann.jis.aws.s3.S3Uploader;
import com.vosmann.jis.aws.s3.Uploader;
import com.vosmann.jis.config.context.S3Config;
import com.vosmann.jis.photo.storage.PhotoMetadataMongoStorage;
import com.vosmann.jis.photo.storage.PhotoMetadataStorage;
import com.vosmann.jis.queue.IdReceiver;
import com.vosmann.jis.queue.IdSender;
import com.vosmann.jis.queue.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;

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
    public IdSender idSender() {
        return new IdSender(Queue.PHOTO_UPLOAD);
    }

    @Bean
    public IdReceiver idReceiver(final PhotoService photoService) {
        final IdReceiver idReceiver = new IdReceiver(Queue.EXIF_EXTRACTION);
        idReceiver.addIdConsumer(photoService);
        return idReceiver;
    }

    @Bean
    public PhotoMetadataStorage photoMetadataStorage(final MongoTemplate mongoTemplate) {
        return new PhotoMetadataMongoStorage(mongoTemplate);
    }

    @Bean
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(2000);
        return factory;
    }


    public static void main(final String[] args) {
        SpringApplication.run(PhotoApp.class);
    }


}
