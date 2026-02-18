//package org.example.fooddelivery.data.repoImpls.cassandra;
//
//import org.jspecify.annotations.Nullable;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.config.CqlSessionFactoryBean;
//import org.springframework.data.cassandra.config.SchemaAction;
//
////@Configuration
//public class CassandraConf extends AbstractCassandraConfiguration {
//
//    @Value("${spring.cassandra.keyspace-name}")
//    private String keyspaceName;
//
//    @Value("${spring.cassandra.contact-points}")
//    private String contactPoints;
//
//    @Value("${spring.cassandra.port}")
//    private int port;
//
//    @Value("${spring.cassandra.username}")
//    private String username;
//
//    @Value("${spring.cassandra.password}")
//    private String password;
//
//    @Value("${spring.cassandra.local-datacenter}")
//    private String datacenter;
//
//    @Override
//    public String getContactPoints() {
//        return contactPoints;
//    }
//
//    @Override
//    protected String getKeyspaceName() {
//        return keyspaceName;
//    }
//
//    @Override
//    public int getPort() {
//        return port;
//    }
//
//    @Override
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.CREATE_IF_NOT_EXISTS;
//    }
//
//    @Override
//    protected @Nullable String getLocalDataCenter() {
//        return datacenter;
//    }
//
//    @Bean
//    @Override
//    public CqlSessionFactoryBean cassandraSession() {
//        CqlSessionFactoryBean session = new CqlSessionFactoryBean();
//        session.setContactPoints(getContactPoints());
//        session.setKeyspaceName(getKeyspaceName());
//        session.setPort(port);
//        session.setUsername(username);
//        session.setPassword(password);
//        session.setLocalDatacenter(datacenter);
//        return session;
//    }
//
//}
