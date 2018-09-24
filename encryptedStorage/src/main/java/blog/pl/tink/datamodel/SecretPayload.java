package blog.pl.tink.datamodel;

import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;

import java.io.IOException;

public class SecretPayload implements IdentifiedDataSerializable {

    private byte[] secret;

    public SecretPayload(byte[] secret) {
        this.secret = secret;
    }

    public SecretPayload(){
        this.secret = new byte[0];
    }

    public byte[] getSecret() {
        return this.secret;
    }

    @Override
    public int getFactoryId() {
        return SecretPayloadFactory.FACTORY_ID;
    }

    @Override
    public int getId() {
        return SecretPayloadFactory.SECRET_PAYLOAD_ID;
    }

    @Override
    public void writeData(ObjectDataOutput out) throws IOException {
        out.writeByteArray(secret);
    }

    @Override
    public void readData(ObjectDataInput in) throws IOException {
        secret = in.readByteArray();
    }
}
