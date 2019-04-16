package br.com.rik.rikchallenge.conf

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.kotlin.KotlinPlugin
import org.jdbi.v3.sqlobject.SqlObjectPlugin
import org.jdbi.v3.sqlobject.kotlin.KotlinSqlObjectPlugin
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.core.env.Environment

@Configuration
@PropertySource("classpath:/persistence.properties")
class PersistenceConf(val environment: Environment) {

    @Bean
    fun jdbi(dataSource: HikariDataSource) = Jdbi.create(dataSource)!!.apply {
        installPlugin(SqlObjectPlugin())
        installPlugin(KotlinPlugin())
        installPlugin(KotlinSqlObjectPlugin())
    }

    @Bean
    fun datasource() = HikariDataSource(
        HikariConfig().apply {
            jdbcUrl = getString("rikchallenge.url")
            username = getString("rikchallenge.user")
            password = getString("rikchallenge.password")
            maximumPoolSize = 30
            minimumIdle = 10
            addDataSourceProperty("useSSL", "false")
            addDataSourceProperty("rewriteBatchedStatements", "true")
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    )

    private fun getString(key: String): String? {
        val envProperty = key.replace(".", "_").toUpperCase()

        return if (environment.containsProperty(envProperty)) {
            environment.getProperty(envProperty)
        } else {
            environment.getProperty(key)
        }
    }
}