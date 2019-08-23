package com.levent.pcd.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.BasicAWSCredentials;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Configuration
@ConfigurationProperties(prefix = "amazon")
//@EnableConfigurationProperties(AWSConfig.class)
@Data
public class AWSConfig {

    @NestedConfigurationProperty
    private Aws aws;
    @NestedConfigurationProperty
    private S3 s3;
    @Getter
    @Setter
    public static class Aws {

        private String accessKeyId;
        private String accessKeySecret;
        @Override
        public String toString() {
            return "Aws{" +
                    "accessKeyId='" + accessKeyId + '\'' +
                    ", accessKeySecret='" + accessKeySecret + '\'' +
                    '}';
        }
    }    
    
    @Getter
    @Setter
    public static class S3 {
        private String defaultBucket;
        private String endPoint;
        private String localPath;
        private String region;
        @Override
        public String toString() {
            return "S3{" +
                    "defaultBucket='" + defaultBucket + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AmazonProperties{" +
                "aws=" + aws +
                ", amazon.s3=" + s3 +
                '}';
    }
    
    
}