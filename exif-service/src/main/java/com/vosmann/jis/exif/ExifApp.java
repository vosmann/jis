package com.vosmann.jis.exif;

import com.vosmann.jis.aws.s3.Downloader;
import com.vosmann.jis.aws.s3.S3Downloader;
import com.vosmann.jis.config.context.S3Config;
import com.vosmann.jis.exif.storage.ExifMetadataSolrStorage;
import com.vosmann.jis.exif.storage.ExifMetadataStorage;
import com.vosmann.jis.queue.IdReceiver;
import com.vosmann.jis.queue.IdSender;
import com.vosmann.jis.queue.Queue;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.util.NamedList;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.io.IOException;

@SpringBootApplication
@Import(S3Config.class)
public class ExifApp {

    @Bean
    public ExifService exifService() {
        return new ExifService();
    }

    @Bean
    public Downloader downloader() {
        return new S3Downloader();
    }

    @Bean
    public IdReceiver idReceiver(final ExifService exifService) {
        final IdReceiver idReceiver = new IdReceiver(Queue.PHOTO_UPLOAD);
        idReceiver.addIdConsumer(exifService);
        return idReceiver;
    }

    @Bean
    public IdSender idSender() {
        return new IdSender(Queue.EXIF_EXTRACTION);
    }

    @Bean
    public ExifMetadataStorage exifMetadataStorage() {
        return new ExifMetadataSolrStorage();
    }

    @Bean
    public SolrServer solrUpdateServer() {
        final HttpSolrServer httpSolrServer = new HttpSolrServer("http://localhost:8983/solr/#/exif/update");
        return httpSolrServer;
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
