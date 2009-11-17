package ar.com.fdvs.dj.test.hibernate;

import org.hibernate.cfg.Configuration;

public class TestSchema {
    
    public static void buildConfiguration() {
    	String db_path = Thread.currentThread().getClass().getResource("/hsql").getPath();
    	
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
        addFile( Thread.currentThread().getClass().getResource("/hibernate/customer.hbm.xml").getFile());

    	HibernateUtil.setSessionFactory(config.buildSessionFactory());    	
    }

}
