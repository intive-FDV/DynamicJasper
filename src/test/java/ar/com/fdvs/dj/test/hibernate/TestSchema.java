package ar.com.fdvs.dj.test.hibernate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;

import java.net.URL;

public class TestSchema {

    private static final Log log = LogFactory.getLog(TestSchema.class);
    
    public static void buildConfiguration() {
        String db_path = TestSchema.class.getResource("/hsql").getPath();

        URL configFile = TestSchema.class.getResource("/hibernate/customer.hbm.xml");
        log.info("Hibernate config file: " + configFile.toString()) ;

        Configuration config = new Configuration().
        setProperty("hibernate.dialect", "org.hibernate.dialect.HSQLDialect").
        setProperty("hibernate.connection.driver_class", "org.hsqldb.jdbcDriver").
        setProperty("hibernate.connection.url", "jdbc:hsqldb:file:" + db_path + "/test_dj_db").
        setProperty("hibernate.connection.username", "sa").
        setProperty("hibernate.connection.password", "").
        setProperty("hibernate.connection.pool_size", "1").
        setProperty("hibernate.connection.autocommit", "true").
        setProperty("hibernate.cache.provider_class", "org.hibernate.cache.HashtableCacheProvider").
        //setProperty("hibernate.hbm2ddl.auto", "create-drop").
        setProperty("hibernate.show_sql", "true").
        addFile( configFile.getFile());

    	HibernateUtil.setSessionFactory(config.buildSessionFactory());    	
    }

}
