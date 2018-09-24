package blog.pl.tink.cache;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import blog.pl.tink.datamodel.SecretPayload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import static com.hazelcast.core.Hazelcast.getHazelcastInstanceByName;

@Service
public class CacheDirectInteractionBeanImpl implements  CacheDirectInteractionBean {

    @Value("${hazelcast.instance.name}")
    private String instanceName;

    @Value("${hazelcast.map.with.secrets.name}")
    private String mapName;

    IMap<String, SecretPayload> secrets;

    @EventListener(ApplicationReadyEvent.class)
    public void afterAppInitialization(){
        HazelcastInstance hazelcastInstance = getHazelcastInstanceByName(instanceName);
        secrets = hazelcastInstance.getMap(mapName);
    }

    @Override
    public void setPayload(String id, SecretPayload secretPayload) {
        secrets.put(id, secretPayload);
    }

    @Override
    public SecretPayload getPayload(String id) {
        return secrets.get(id);
    }
}
