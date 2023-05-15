package eu.drone.drones

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.containers.GenericContainer
import spock.lang.Specification

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class IntegrationTest extends Specification {

    private static final def USER = 'postgres'
    private static final def DB = 'drones'
    private static final def PWD = 'postgres'
    private static final def EXPOSED_PORT = 5432

    private static final GenericContainer Postgres =
            new GenericContainer("postgres:15")
                    .withEnv('POSTGRES_USER', USER)
                    .withEnv('POSTGRES_DB', DB)
                    .withEnv('POSTGRES_PASSWORD', PWD)
                    .withExposedPorts(EXPOSED_PORT)

    def setupSpec() {
        Postgres.start();
    }

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry propertyRegistry) {
        propertyRegistry.add('spring.datasource.url') { jdbcUrlToContainer() }
        propertyRegistry.add('spring.datasource.username') { USER }
        propertyRegistry.add('spring.datasource.password') { PWD }
    }

    private static def jdbcUrlToContainer() {
        def host = Postgres.getHost()
        def port = Postgres.getMappedPort(EXPOSED_PORT)

        return "jdbc:postgresql://$host:$port/$DB"
    }
}
