package br.com.rik.rikchallenge.conf

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("br.com.rik.**")
class DatabaseTestConfig {

    @Bean
    fun jdbi(dataSource: HikariDataSource) = Jdbi.create(dataSource)!!.apply {
        installPlugin(SqlObjectPlugin())
        installPlugin(KotlinPlugin())
        installPlugin(KotlinSqlObjectPlugin())
    }

    @Bean
    fun datasource() = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = "jdbc:h2:mem:test;MODE=MySQL;DB_CLOSE_ON_EXIT=FALSE;MVCC=FALSE"
            username = "sa"
            password = ""
            maximumPoolSize = 60
            minimumIdle = 20
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    )

}