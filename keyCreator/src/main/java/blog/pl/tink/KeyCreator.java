package blog.pl.tink;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.KmsClient;
import com.google.crypto.tink.aead.AeadKeyTemplates;
import com.google.crypto.tink.config.TinkConfig;
import com.google.crypto.tink.integration.gcpkms.GcpKmsClient;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class KeyCreator {

    public static final String MASTER_KEY_LOCATION;
    static {
        MASTER_KEY_LOCATION = "gcp-kms://projects/name_of_your_project/locations/global/keyRings/my-app-keyring/cryptoKeys/master-key";
    }


    public static void main(String[] args) throws GeneralSecurityException, IOException {
        TinkConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES128_GCM);
        KmsClient client = new GcpKmsClient().withDefaultCredentials();

        final Aead msterKey = client.getAead(MASTER_KEY_LOCATION);
        keysetHandle.write(new GcpWriter("key.json", "keys-for-my-app"), msterKey);

    }
}
