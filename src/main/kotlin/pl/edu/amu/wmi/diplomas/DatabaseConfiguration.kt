package pl.edu.amu.wmi.diplomas

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

val DATABASE_URL: String = System.getenv("DB_URL")
val DATABASE_PASSWORD: String = System.getenv("DB_PASSWORD")
val DATABASE_USERNAME: String = System.getenv("DB_USERNAME")

fun configureDatabase() {
    val dataSource = provideDataSource()
    Database.connect(dataSource)
    initMigrationTool(dataSource)
}

private fun initMigrationTool(dataSource: HikariDataSource) {
    Flyway.configure()
        .dataSource(dataSource)
        .load()
        .migrate()
}

private fun provideDataSource(): HikariDataSource {
    val config = HikariConfig()
    config.driverClassName = "org.postgresql.Driver"
    config.jdbcUrl = DATABASE_URL
    config.username = DATABASE_USERNAME
    config.password = DATABASE_PASSWORD
    config.maximumPoolSize = 3
    config.connectionTimeout = 20000
    config.isAutoCommit = true
    config.transactionIsolation = "TRANSACTION_READ_COMMITTED"
    config.validate()
    return HikariDataSource(config)
}