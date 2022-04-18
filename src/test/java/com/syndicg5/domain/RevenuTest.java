package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RevenuTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Revenu.class);
        Revenu revenu1 = new Revenu();
        revenu1.setId("id1");
        Revenu revenu2 = new Revenu();
        revenu2.setId(revenu1.getId());
        assertThat(revenu1).isEqualTo(revenu2);
        revenu2.setId("id2");
        assertThat(revenu1).isNotEqualTo(revenu2);
        revenu1.setId(null);
        assertThat(revenu1).isNotEqualTo(revenu2);
    }
}
