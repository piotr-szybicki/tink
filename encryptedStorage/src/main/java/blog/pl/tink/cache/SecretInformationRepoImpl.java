package blog.pl.tink.cache;

import blog.pl.tink.datamodel.UserPostsDto;
import blog.pl.tink.datamodel.SecretPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SecretInformationRepoImpl implements SecretInformationRepo {

    @Autowired
    CacheDirectInteractionBean cacheDirectInteractionBean;

    @Autowired
    CacheObjectEncryption encryptor;

    @Override
    public void save(UserPostsDto buissnessObject) {
        final byte[] buissnessObjectEncrypted = encryptor.encrypt(buissnessObject);
        cacheDirectInteractionBean.setPayload(buissnessObject.id, new SecretPayload(buissnessObjectEncrypted));
    }

    @Override
    public UserPostsDto fetchById(String key) {
        SecretPayload secretPayload = cacheDirectInteractionBean.getPayload(key);
        return encryptor.decrypt(secretPayload.getSecret(), key);
    }
}
