package fr.insy2s.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import fr.insy2s.web.rest.TestUtil;

public class HistoriqueTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Historique.class);
        Historique historique1 = new Historique();
        historique1.setId(1L);
        Historique historique2 = new Historique();
        historique2.setId(historique1.getId());
        assertThat(historique1).isEqualTo(historique2);
        historique2.setId(2L);
        assertThat(historique1).isNotEqualTo(historique2);
        historique1.setId(null);
        assertThat(historique1).isNotEqualTo(historique2);
    }
}
