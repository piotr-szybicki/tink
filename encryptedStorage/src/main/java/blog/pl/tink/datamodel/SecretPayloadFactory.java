package blog.pl.tink.datamodel;

import com.hazelcast.nio.serialization.DataSerializableFactory;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import blog.pl.tink.datamodel.SecretPayload;

public class SecretPayloadFactory implements DataSerializableFactory {

    public static final int FACTORY_ID = 1;
    public static final int SECRET_PAYLOAD_ID = 100;

    @Override
    public IdentifiedDataSerializable create(int typeId) {
        if(typeId == SECRET_PAYLOAD_ID) {
            return new SecretPayload(new byte[0]);
        } else {
            throw new RuntimeException("exception");
        }
    }
}

