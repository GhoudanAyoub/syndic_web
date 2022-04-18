package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SyndicTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Syndic.class);
        Syndic syndic1 = new Syndic();
        syndic1.setId("id1");
        Syndic syndic2 = new Syndic();
        syndic2.setId(syndic1.getId());
        assertThat(syndic1).isEqualTo(syndic2);
        syndic2.setId("id2");
        assertThat(syndic1).isNotEqualTo(syndic2);
        syndic1.setId(null);
        assertThat(syndic1).isNotEqualTo(syndic2);
    }
}
