package blog.pl.tink.cache;

import blog.pl.tink.datamodel.SecretPayload;
import blog.pl.tink.datamodel.UserPostsDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SecretInformationRepoImplTest {

    @InjectMocks
    private SecretInformationRepoImpl repo;

    @Mock
    private CacheDirectInteractionBean cacheDirectInteraction;

    @Mock
    private CacheObjectEncryption cacheObjectEncryption;

    @Test
    public void canaryTest(){
        assertThat(repo).isNotNull();
        assertThat(cacheDirectInteraction).isNotNull();
    }

    @Test
    public void shouldInjectMocks() {
        //given
        final String id = "1";
        final SecretPayload payloadStoredInChache = new SecretPayload();
        when(cacheDirectInteraction.getPayload(anyString())).thenReturn(payloadStoredInChache);
        when(cacheObjectEncryption.decrypt(any(), anyString())).thenReturn(new UserPostsDto(id, "1", "1"));


        //when
        final UserPostsDto user = repo.fetchById(id);

        //then
        verify(cacheObjectEncryption, times(1)).decrypt(any(), anyString());
        assertThat(user).isNotNull();
    }

}