package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ImmeubleTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Immeuble.class);
        Immeuble immeuble1 = new Immeuble();
        immeuble1.setId("id1");
        Immeuble immeuble2 = new Immeuble();
        immeuble2.setId(immeuble1.getId());
        assertThat(immeuble1).isEqualTo(immeuble2);
        immeuble2.setId("id2");
        assertThat(immeuble1).isNotEqualTo(immeuble2);
        immeuble1.setId(null);
        assertThat(immeuble1).isNotEqualTo(immeuble2);
    }
}
