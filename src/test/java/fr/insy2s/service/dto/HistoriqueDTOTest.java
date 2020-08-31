package fr.insy2s.service.dto;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.insy2s.web.rest.TestUtil;

public class HistoriqueDTOTest {

    @Test
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(HistoriqueDTO.class);
        HistoriqueDTO historiqueDTO1 = new HistoriqueDTO();
        historiqueDTO1.setId(1L);
        HistoriqueDTO historiqueDTO2 = new HistoriqueDTO();
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
        historiqueDTO2.setId(historiqueDTO1.getId());
        assertThat(historiqueDTO1).isEqualTo(historiqueDTO2);
        historiqueDTO2.setId(2L);
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
        historiqueDTO1.setId(null);
        assertThat(historiqueDTO1).isNotEqualTo(historiqueDTO2);
    }
}
