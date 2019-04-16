package br.com.rik.rikchallenge

import br.com.rik.rikchallenge.conf.DatabaseTestConfig
import org.jdbi.v3.core.Handle
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.core.locator.ClasspathSqlLocator
import org.junit.Before
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = [(DatabaseTestConfig::class)])
abstract class Setup {

    @Autowired
    lateinit var jdbi: Jdbi

    @Before
    fun setUpDB() {
        jdbi.withHandle<Unit, Exception> { handle: Handle ->
            val createSQL = ClasspathSqlLocator.findSqlOnClasspath("sql/CreateTables")
            handle.execute(createSQL)
        }
    }

}