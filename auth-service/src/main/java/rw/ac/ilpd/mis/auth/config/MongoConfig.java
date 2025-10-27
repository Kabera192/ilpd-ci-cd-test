package rw.ac.ilpd.mis.auth.config;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 10/07/2025
 */

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

//@Configuration
//@EnableMongoRepositories(
//        basePackages = "rw.ac.ilpd.mis.auth.repository.mongo",
//        mongoTemplateRef = "mongoTemplate"
//)
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.host}")
    protected String mongoHost;

    @Value("${spring.data.mongodb.port}")
    protected String mongoPort;

    @Value("${spring.data.mongodb.database}")
    protected String mongoDb;

    @Override
    protected String getDatabaseName(){
        return getMongoDb();
    }

    protected String getMongoUrl(){
        return "mongodb://" + getMongoHost() + ":"+getMongoPort();
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return MongoClients.create(getMongoUrl());
    }

    public String getMongoHost() {
        return mongoHost;
    }

    public String getMongoPort() {
        return mongoPort;
    }

    public String getMongoDb() {
        return mongoDb;
    }
}

