package blog.pl.tink.cache;

import com.hazelcast.config.Config;
import com.hazelcast.config.SerializationConfig;
import blog.pl.tink.datamodel.SecretPayloadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Value("${hazelcast.instance.name}")
    String hazelcastInstanceName;

    @Bean
    public Config hazelCastConfig() {
        final SerializationConfig serializationConfig = new SerializationConfig();
        serializationConfig.addDataSerializableFactory(SecretPayloadFactory.FACTORY_ID, new SecretPayloadFactory());
        return new Config()
                .setInstanceName(hazelcastInstanceName)
                .setSerializationConfig(serializationConfig);
    }

}
