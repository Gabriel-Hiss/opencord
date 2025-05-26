package net.opencord.config;

import org.springframework.boot.web.embedded.tomcat.TomcatProtocolHandlerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@Configuration
public class VirtualThreadConfig {

    @Bean
    public ThreadFactory virtualThreadFactory() {
        return Thread.ofVirtual().name("vthread-", 0).factory();
    }

    @Bean
    public TomcatProtocolHandlerCustomizer<?> protocolHandlerCustomizer(ThreadFactory virtualThreadFactory) {
        return protocolHandler -> protocolHandler.setExecutor(Executors.newThreadPerTaskExecutor(virtualThreadFactory));
    }
}