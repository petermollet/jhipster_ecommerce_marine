package fr.insy2s.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class TypeArticleMapperTest {

    private TypeArticleMapper typeArticleMapper;

    @BeforeEach
    public void setUp() {
        typeArticleMapper = new TypeArticleMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(typeArticleMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(typeArticleMapper.fromId(null)).isNull();
    }
}
