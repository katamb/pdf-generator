package integration_tests;

import org.testcontainers.containers.PostgreSQLContainer;

/**
 * Code created with help from: https://www.baeldung.com/spring-boot-testcontainers-integration-test
 */
public class BasePostgreSqlContainer extends PostgreSQLContainer<BasePostgreSqlContainer> {

    private static final String IMAGE_VERSION = "postgres:12.2";
    private static BasePostgreSqlContainer container;

    private BasePostgreSqlContainer() {
        super(IMAGE_VERSION);
    }

    public static BasePostgreSqlContainer getInstance() {
        if (container == null) {
            container = new BasePostgreSqlContainer();
            container.start();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
        // do nothing, JVM handles shut down
    }

}
