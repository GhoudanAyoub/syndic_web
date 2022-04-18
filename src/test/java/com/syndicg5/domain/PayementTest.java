package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PayementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payement.class);
        Payement payement1 = new Payement();
        payement1.setId("id1");
        Payement payement2 = new Payement();
        payement2.setId(payement1.getId());
        assertThat(payement1).isEqualTo(payement2);
        payement2.setId("id2");
        assertThat(payement1).isNotEqualTo(payement2);
        payement1.setId(null);
        assertThat(payement1).isNotEqualTo(payement2);
    }
}
