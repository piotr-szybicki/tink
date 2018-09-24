package blog.pl.tink.cache;

import blog.pl.tink.datamodel.UserPostsDto;

public interface SecretInformationRepo {

    public void save(UserPostsDto buissnessObject);

    public UserPostsDto fetchById(String key);

}
