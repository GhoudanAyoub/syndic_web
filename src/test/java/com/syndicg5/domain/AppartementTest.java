package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppartementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appartement.class);
        Appartement appartement1 = new Appartement();
        appartement1.setId("id1");
        Appartement appartement2 = new Appartement();
        appartement2.setId(appartement1.getId());
        assertThat(appartement1).isEqualTo(appartement2);
        appartement2.setId("id2");
        assertThat(appartement1).isNotEqualTo(appartement2);
        appartement1.setId(null);
        assertThat(appartement1).isNotEqualTo(appartement2);
    }
}
