package blog.pl.tink;

import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetWriter;
import com.google.crypto.tink.proto.EncryptedKeyset;
import com.google.crypto.tink.proto.Keyset;
import org.apache.http.entity.ContentType;

import java.io.*;

import static org.apache.http.entity.ContentType.APPLICATION_JSON;

public class GcpWriter implements KeysetWriter {

    private Bucket bucket;

    private final String keyName;

    public GcpWriter(String keyName, String bucketName){
        this.keyName = keyName;
        Storage storage = StorageOptions.getDefaultInstance().getService();
        bucket = storage.get(bucketName);
    }

    @Override
    public void write(Keyset keyset) throws IOException {
        throw new IOException("Do not try to save unencrypted key.");
    }

    @Override
    public void write(EncryptedKeyset keyset) throws IOException {
        ByteArrayOutputStream outputStructureForTheKey = new ByteArrayOutputStream();
        final KeysetWriter keysetWriter = JsonKeysetWriter.withOutputStream(outputStructureForTheKey);
        keysetWriter.write(keyset);

        ByteArrayInputStream encryptedKey = new ByteArrayInputStream(outputStructureForTheKey.toByteArray());
        Blob blob = bucket.create(keyName, encryptedKey, APPLICATION_JSON.getMimeType());
    }
}
