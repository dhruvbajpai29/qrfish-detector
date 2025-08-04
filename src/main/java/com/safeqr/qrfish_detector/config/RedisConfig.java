package com.safeqr.qrfish_detector.config;

import io.lettuce.core.RedisURI;
import io.lettuce.core.resource.ClientResources;
import io.lettuce.core.resource.DefaultClientResources;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.time.Duration;

@Configuration
public class RedisConfig {

    @Bean(destroyMethod = "shutdown")
    public ClientResources clientResources() {
        return DefaultClientResources.create();
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(ClientResources clientResources) {
        RedisStandaloneConfiguration serverConfig = new RedisStandaloneConfiguration();
        serverConfig.setHostName("suitable-marmot-9003.upstash.io");
        serverConfig.setPort(6379);
        serverConfig.setUsername("default");
        serverConfig.setPassword("ASMrAAIjcDE5ZjYwNTBkM2ExMjY0M2I4OWY2ZTBjMmNhMjgzMDliMnAxMA");

        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl()
//                .commandTimeout(Duration.ofSeconds(5))
//                .clientResources(clientResources)
                .build();


        return new LettuceConnectionFactory(serverConfig, clientConfig);
    }
}
