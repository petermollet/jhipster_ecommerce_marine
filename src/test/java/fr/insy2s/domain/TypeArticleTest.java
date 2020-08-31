package fr.insy2s.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.insy2s.web.rest.TestUtil;

public class TypeArticleTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TypeArticle.class);
        TypeArticle typeArticle1 = new TypeArticle();
        typeArticle1.setId(1L);
        TypeArticle typeArticle2 = new TypeArticle();
        typeArticle2.setId(typeArticle1.getId());
        assertThat(typeArticle1).isEqualTo(typeArticle2);
        typeArticle2.setId(2L);
        assertThat(typeArticle1).isNotEqualTo(typeArticle2);
        typeArticle1.setId(null);
        assertThat(typeArticle1).isNotEqualTo(typeArticle2);
    }
}
