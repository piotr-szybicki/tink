package blog.pl.tink;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetReader;
import com.google.crypto.tink.proto.EncryptedKeyset;
import com.google.crypto.tink.proto.Keyset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class GcpReader implements KeysetReader {

    private Bucket bucket;

    @Value("${key.name}")
    String privateKeyName;

    @Value("${key.bucket.name}")
    String privateKeyBucketName;

    @PostConstruct
    public void init() {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        bucket = storage.get(privateKeyBucketName);
    }

    @Override
    public Keyset read() throws IOException {
        throw new RuntimeException("Do not try to read unencrypted key");
    }

    @Override
    public EncryptedKeyset readEncrypted() throws IOException {
        final Blob blob = bucket.get(privateKeyName);
        final byte[] privateKeyInBinary = blob.getContent();
        return JsonKeysetReader.withBytes(privateKeyInBinary).readEncrypted();
    }
}
