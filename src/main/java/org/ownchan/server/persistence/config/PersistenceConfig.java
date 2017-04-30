/*******************************************************************************
 * @author Stefan Gündhör <stefan@guendhoer.com>
 *
 * @copyright Copyright (c) 2017, Stefan Gündhör <stefan@guendhoer.com>
 * @license AGPL-3.0
 *
 * This code is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License, version 3,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License, version 3,
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *******************************************************************************/
package org.ownchan.server.persistence.config;

import java.util.Arrays;
import java.util.HashSet;

import javax.sql.DataSource;

import org.apache.ibatis.session.AutoMappingBehavior;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.ownchan.server.persistence.model.PersistableObjectScanBaseMarker;
import org.ownchan.server.persistence.typehandler.BooleanTypeHandler;
import org.ownchan.server.persistence.typehandler.GeometryCollectionTypeHandler;
import org.ownchan.server.persistence.typehandler.GeometryTypeHandler;
import org.ownchan.server.persistence.typehandler.LineStringTypeHandler;
import org.ownchan.server.persistence.typehandler.MultiLineStringTypeHandler;
import org.ownchan.server.persistence.typehandler.MultiPointTypeHandler;
import org.ownchan.server.persistence.typehandler.MultiPolygonTypeHandler;
import org.ownchan.server.persistence.typehandler.NullablePointTypeHandler;
import org.ownchan.server.persistence.typehandler.PointTypeHandler;
import org.ownchan.server.persistence.typehandler.PolygonTypeHandler;
import org.ownchan.server.persistence.typehandler.PrimitiveBooleanTypeHandler;
import org.ownchan.server.persistence.typehandler.auto.TypeHandlerScanBaseMarker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@PropertySource(value = { "classpath:/config/db/server/config.properties" })
@EnableTransactionManagement
@MapperScan("org.ownchan.persistence.mapper")
@Configuration("ownchan-server-persistence-config")
public class PersistenceConfig {

  @Value("${ownchan.server.db.defaultFetchSize:750}")
  private int defaultFetchSize;

  @ConfigurationProperties(prefix = "datasource.ownchan", ignoreUnknownFields = false)
  @Bean
  @Primary
  public DataSource dataSource() {
    return new HikariDataSource();
  }

  @Bean
  @Primary
  public PlatformTransactionManager transactionManager() {
    return new DataSourceTransactionManager(dataSource());
  }

  @Bean
  @Primary
  public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = createSqlSessionFactory();
    sessionFactory.setDataSource(dataSource());
    return sessionFactory;
  }

  @Bean
  @Primary
  public SqlSessionTemplate sqlSessionTemplate() throws Exception {
    return new SqlSessionTemplate(sqlSessionFactory().getObject(), ExecutorType.BATCH);
  }

  private SqlSessionFactoryBean createSqlSessionFactory() throws Exception {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setConfiguration(createOwnchanServerMybatisConfiguration(defaultFetchSize));
    sessionFactory.setTypeAliasesPackage(PersistableObjectScanBaseMarker.class.getPackage().getName());
    // we want to register some handlers in the correct order ...
    sessionFactory.setTypeHandlers(new TypeHandler[] {
        new BooleanTypeHandler(),
        new PrimitiveBooleanTypeHandler(),
        new NullablePointTypeHandler(),
        new PointTypeHandler(),
        new LineStringTypeHandler(),
        new PolygonTypeHandler(),
        new MultiPointTypeHandler(),
        new MultiLineStringTypeHandler(),
        new MultiPolygonTypeHandler(),
        new GeometryCollectionTypeHandler(),
        new GeometryTypeHandler()
    });
    sessionFactory.setTypeHandlersPackage(TypeHandlerScanBaseMarker.class.getPackage().getName());
    sessionFactory.setVfs(SpringBootVFS.class);
    return sessionFactory;
  }

  /**
   * Create a Mybatis Configuration instance for ownchan-server.
   * @param defaultFetchSize if not set (null), then the default fetch size of the applicable JDBC driver will be used
   */
  public static org.apache.ibatis.session.Configuration createOwnchanServerMybatisConfiguration(Integer defaultFetchSize) {
    org.apache.ibatis.session.Configuration config = new org.apache.ibatis.session.Configuration();
    /*
     * We do use ehcache, but we avoid using org.mybatis.caches.ehcache.EhcacheCache
     * and use dto service method level caching annotations instead.
     */
    config.setCacheEnabled(false);
    config.setLazyLoadingEnabled(true);
    config.setAggressiveLazyLoading(false);
    config.setLazyLoadTriggerMethods(new HashSet<>(Arrays.asList("clone", "detach")));
    config.setJdbcTypeForNull(JdbcType.NULL);
    config.setDefaultExecutorType(ExecutorType.BATCH);
    config.setAutoMappingBehavior(AutoMappingBehavior.NONE);
    config.setVfsImpl(SpringBootVFS.class);
    config.setDefaultFetchSize(defaultFetchSize);

    return config;
  }

}
