package fr.insy2s.service.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HistoriqueMapperTest {

    private HistoriqueMapper historiqueMapper;

    @BeforeEach
    public void setUp() {
        historiqueMapper = new HistoriqueMapperImpl();
    }

    @Test
    public void testEntityFromId() {
        Long id = 1L;
        assertThat(historiqueMapper.fromId(id).getId()).isEqualTo(id);
        assertThat(historiqueMapper.fromId(null)).isNull();
    }
}
