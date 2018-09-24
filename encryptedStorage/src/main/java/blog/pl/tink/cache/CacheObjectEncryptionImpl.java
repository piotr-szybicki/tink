package blog.pl.tink.cache;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.KmsClient;
import com.google.crypto.tink.aead.AeadFactory;
import com.google.crypto.tink.integration.gcpkms.GcpKmsClient;
import com.google.gson.Gson;
import blog.pl.tink.GcpReader;
import blog.pl.tink.datamodel.UserPostsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

@Component
public class CacheObjectEncryptionImpl implements CacheObjectEncryption {

    private Aead aead = null;

    @Value("${gcp.master.key.location}")
    String masterKeyLocation;

    @Autowired
    GcpReader masterKeyReder;

    @PostConstruct
    private void readKey() throws GeneralSecurityException, IOException {
        KmsClient client = new GcpKmsClient().withDefaultCredentials();
        final Aead msterKey = client.getAead(masterKeyLocation);
        KeysetHandle keysetHandle = KeysetHandle.read(masterKeyReder, msterKey);
        aead = AeadFactory.getPrimitive(keysetHandle);
    }

    @Override
    public byte[] encrypt(UserPostsDto buissnessData){
        try {
            Gson gson = new Gson();
            return aead.encrypt(gson.toJson(buissnessData).getBytes(), buissnessData.id.getBytes());
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("somthing went wrong");
        }
    }

    @Override
    public UserPostsDto decrypt(byte[] data, String id){
        final byte[] decrypted;
        try {
            decrypted = aead.decrypt(data, id.getBytes());
            Gson gson = new Gson();
            InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(decrypted));
            return gson.fromJson(isr, UserPostsDto.class);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException("somthing went wrong");
        }
    }
}
