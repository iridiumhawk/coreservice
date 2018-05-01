package com.cherkasov;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {

        return "cyprus";
    }

    @Override
    public Mongo mongo() throws Exception {

        return new MongoClient("localhost", 27017);
    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        // TODO: 01.05.2018 make our package?
        return super.getMappingBasePackages();
    }
}
