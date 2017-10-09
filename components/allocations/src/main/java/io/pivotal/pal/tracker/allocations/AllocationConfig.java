package io.pivotal.pal.tracker.allocations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

//@Configuration
public class AllocationConfig {

    @Bean
    JedisConnectionFactory jedisConnectionFactory()
    {
        JedisConnectionFactory conn = new JedisConnectionFactory();
        conn.setHostName("localhost");
        return conn;
    }

    @Bean
    RedisTemplate<Long, Object> redisTemplate() {

        RedisTemplate<Long, Object> template = new RedisTemplate<Long, Object>();

        template.setConnectionFactory(jedisConnectionFactory());

        // these are required to ensure keys and values are correctly serialized

        template.setKeySerializer(new StringRedisSerializer());

        template.setHashValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        template.setValueSerializer(new GenericToStringSerializer<Object>(Object.class));

        return template;

    }



}
