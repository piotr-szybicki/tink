package blog.pl.tink.cache;

import blog.pl.tink.datamodel.UserPostsDto;

public interface CacheObjectEncryption {

    byte[] encrypt(UserPostsDto businessData);

    UserPostsDto decrypt(byte[] data, String id);
}
