package blog.pl.tink.datamodel;

public class UserPostsDto {

    public String id;
    public String name;
    public String post;

    public UserPostsDto() {
    }

    public UserPostsDto(String id, String name, String post) {
        this.id = id;
        this.name = name;
        this.post = post;
    }
}
