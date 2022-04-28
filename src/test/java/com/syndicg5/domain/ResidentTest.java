package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResidentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resident.class);
        Resident resident1 = new Resident();
        resident1.setId(1L);
        Resident resident2 = new Resident();
        resident2.setId(resident1.getId());
        assertThat(resident1).isEqualTo(resident2);
        resident2.setId(2L);
        assertThat(resident1).isNotEqualTo(resident2);
        resident1.setId(null);
        assertThat(resident1).isNotEqualTo(resident2);
    }
}
