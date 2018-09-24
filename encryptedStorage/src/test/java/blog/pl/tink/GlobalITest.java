package blog.pl.tink;

import com.google.crypto.tink.config.TinkConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import blog.pl.tink.datamodel.UserPostsDto;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.GeneralSecurityException;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest({"hazelcast.instance.name=hazelcast-instance",
                 "hazelcast.map.with.secrets.name=map-with-secrets" ,
                 "key.name=key.json",
                 "key.bucket.name=keys-for-my-app",
                 "gcp.master.key.location=gcp-kms://projects/name_of_your_project/locations/global/keyRings/my-app-keyring/cryptoKeys/master-key"})
public class GlobalITest {

    @Autowired
    Controller controller;
    public static final String POST_TEXT = "some text";
    public static final String USERID = "user123";

    @BeforeClass
    public static void initKeyManager() throws GeneralSecurityException {
        TinkConfig.register();
    }

    @Test
    public void controller_notNull(){
        assertThat(controller).isNotNull();
    }

    @Test
    public void storeObject_checkIfObjectAppearsInCache(){
        final String id = UUID.randomUUID().toString();
        final UserPostsDto dto = new UserPostsDto(id, USERID, POST_TEXT);
        controller.storeObject(dto);

        final HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("hazelcast-instance");
        final IMap<String, Object> map = instance.getMap("map-with-secrets");
        assertThat(map).containsKey(id);
    }

    @Test
    public void storeObject_checkIfObjectDecrypts(){
        final String id = UUID.randomUUID().toString();
        final UserPostsDto dto = new UserPostsDto(id, USERID, POST_TEXT);
        controller.storeObject(dto);

        final UserPostsDto userPostsDto = controller.fetchObject(id);
        assertThat(userPostsDto).isNotNull();
        assertThat(userPostsDto.id).isEqualTo(id);
        assertThat(userPostsDto.name).isEqualTo(USERID);
        assertThat(userPostsDto.post).isEqualTo(POST_TEXT);
    }

    @SpringBootApplication
    static class SpringTestDummyClass {}
}
