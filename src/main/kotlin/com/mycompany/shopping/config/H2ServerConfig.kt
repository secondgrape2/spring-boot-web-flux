package com.mycompany.shopping.config

import org.h2.tools.Server
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextClosedEvent
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import java.sql.SQLException

@Configuration
class H2ConsoleConfig {

    private val logger = LoggerFactory.getLogger(H2ConsoleConfig::class.java)

    @Value("\${spring.h2.console.port:8090}")
    private lateinit var port: String

    private lateinit var webServer: Server

    @EventListener(ContextRefreshedEvent::class)
    fun start() {
        try {
            logger.info("started h2 console at port {}.", port)
            webServer = Server.createWebServer("-webPort", port, "-webAllowOthers").start()
        } catch (e: SQLException) {
            logger.error("Failed to start H2 console: {}", e.message)
        }
    }

    @EventListener(ContextClosedEvent::class)
    fun stop() {
        if (::webServer.isInitialized) {
            logger.info("stopped h2 console at port {}.", port)
            webServer.stop()
        }
    }
}