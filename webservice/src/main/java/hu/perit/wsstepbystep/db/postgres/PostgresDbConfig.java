/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.perit.wsstepbystep.db.postgres;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.resource.jdbc.spi.PhysicalConnectionHandlingMode;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import hu.perit.spvitamin.spring.data.config.DatasourceCollectionProperties;
import hu.perit.spvitamin.spring.data.dynamicdatasource.ConnectionParam;
import hu.perit.spvitamin.spring.data.dynamicdatasource.DynamicDataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * #know-how:hibernate-configuration
 * #know-how:jpa-auditing
 *
 * @author Peter Nagy
 */

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = PostgresDbConfig.PACKAGES, entityManagerFactoryRef = PostgresDbConfig.ENTITY_MANAGER_FACTORY, transactionManagerRef = PostgresDbConfig.TRANSACTION_MANAGER)
@Slf4j
public class PostgresDbConfig
{
    static final String PACKAGES = "hu.perit.wsstepbystep.db.postgres";
    static final String ENTITY_MANAGER_FACTORY = "entityManagerFactory";
    static final String TRANSACTION_MANAGER = "transactionManager";

    private static final String PERSISTENCE_UNIT = "postgres";		//ez ami meg kell egyezzen az app-prop-ben a postgres -el:  datasource.postgres.db-type=postgresql  
    private static final String DATASOURCE = "dataSource";

    private final ConnectionParam connectionParam;

    public PostgresDbConfig(DatasourceCollectionProperties dbProperties)
    {
        this.connectionParam = new ConnectionParam(dbProperties.getDatasource().get(PERSISTENCE_UNIT));
    }


    @Primary
    @Bean(name = DATASOURCE)
    @DependsOn("SpvitaminSpringContext")
    public DataSource dataSource()
    {
        log.debug(String.format("creating DataSource for '%s'", PERSISTENCE_UNIT));

        // False bug report: Use try-with-resources or close this "DynamicDataSource" in a "finally" clause
        DynamicDataSource ds = new DynamicDataSource(); // NOSONAR

        ds.setConnectionParam(this.connectionParam);

        return ds;
    }

    @Primary
    @Bean(name = ENTITY_MANAGER_FACTORY)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
        @Qualifier(DATASOURCE) DataSource dataSource)
    {
        Map<String, String> properties = new HashMap<>();
        properties.put("hibernate.dialect", this.connectionParam.getDialect());
        properties.put("hibernate.connection.handling_mode",
            PhysicalConnectionHandlingMode.DELAYED_ACQUISITION_AND_RELEASE_AFTER_TRANSACTION.name()); //l??trehoz egy connection pool-t, gyorsabb ??gy. hikary? conn. probl??m??n??l helyre??ll stb.
        properties.put("hibernate.hbm2ddl.auto", this.connectionParam.getDdlAuto());

        return builder.dataSource(dataSource).packages(PACKAGES).persistenceUnit(PERSISTENCE_UNIT).properties(properties).build();
    }

    @Primary
    @Bean(name = TRANSACTION_MANAGER)
    public PlatformTransactionManager transactionManager(@Qualifier(ENTITY_MANAGER_FACTORY) EntityManagerFactory entityManagerFactory)
    {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
