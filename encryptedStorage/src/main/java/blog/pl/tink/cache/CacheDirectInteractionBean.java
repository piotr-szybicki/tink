package blog.pl.tink.cache;

import blog.pl.tink.datamodel.SecretPayload;

public interface CacheDirectInteractionBean {

    public void setPayload(String id, SecretPayload secretPayload);

    public SecretPayload getPayload(String id);
}
