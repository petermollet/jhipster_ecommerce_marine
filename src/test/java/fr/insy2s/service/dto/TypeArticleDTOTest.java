package fr.insy2s.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.insy2s.web.rest.TestUtil;

public class TypeArticleDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeArticleDTO.class);
        TypeArticleDTO typeArticleDTO1 = new TypeArticleDTO();
        typeArticleDTO1.setId(1L);
        TypeArticleDTO typeArticleDTO2 = new TypeArticleDTO();
        assertThat(typeArticleDTO1).isNotEqualTo(typeArticleDTO2);
        typeArticleDTO2.setId(typeArticleDTO1.getId());
        assertThat(typeArticleDTO1).isEqualTo(typeArticleDTO2);
        typeArticleDTO2.setId(2L);
        assertThat(typeArticleDTO1).isNotEqualTo(typeArticleDTO2);
        typeArticleDTO1.setId(null);
        assertThat(typeArticleDTO1).isNotEqualTo(typeArticleDTO2);
    }
}
