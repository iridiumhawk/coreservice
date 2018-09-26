package com.cherkasov;


import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import java.util.ArrayList;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;

@Configuration
@EnableMongoRepositories
public class MongoConfig extends AbstractMongoConfiguration {

    private int CONNECTION_TIME_OUT_MS = 10000;
    private int SOCKET_TIME_OUT_MS = 0;
    private int SERVER_SELECTION_TIMEOUT_MS = 5000;

    private String username = "admin";
    private String password = "password";

    @Override
    protected String getDatabaseName() {

        return "cyprus";
    }

    @Override
    public Mongo mongo() throws Exception {

        List<MongoCredential> creds = new ArrayList<MongoCredential>();
        creds.add(MongoCredential.createCredential(username, getDatabaseName(), password.toCharArray()));

        MongoClientOptions.Builder optionsBuilder = MongoClientOptions.builder();

        optionsBuilder.connectTimeout(CONNECTION_TIME_OUT_MS);

        optionsBuilder.socketTimeout(SOCKET_TIME_OUT_MS);

        optionsBuilder.serverSelectionTimeout(SERVER_SELECTION_TIMEOUT_MS);

        MongoClientOptions options = optionsBuilder.build();

        return new MongoClient(new ServerAddress("localhost" , 27017), options);

    }

    @Override
    protected Collection<String> getMappingBasePackages() {
        // TODO: 01.05.2018 make our package?
        return super.getMappingBasePackages();
    }
}
