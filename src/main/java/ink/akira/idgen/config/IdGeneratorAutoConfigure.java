package ink.akira.idgen.config;

import ink.akira.idgen.snowflake.IdGenerator;
import ink.akira.idgen.snowflake.SnowFlakeIdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(IdGeneratorProperties.class)
public class IdGeneratorAutoConfigure {
    @Autowired
    private IdGeneratorProperties idGeneratorProperties;

    @Bean
    public IdGenerator iSnowFlakeIdGenerator() {
        return new SnowFlakeIdGenerator(idGeneratorProperties.getIdType(), idGeneratorProperties.getWorkerId());
    }
}
