package com.syndicg5.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.syndicg5.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorie.class);
        Categorie categorie1 = new Categorie();
        categorie1.setId("id1");
        Categorie categorie2 = new Categorie();
        categorie2.setId(categorie1.getId());
        assertThat(categorie1).isEqualTo(categorie2);
        categorie2.setId("id2");
        assertThat(categorie1).isNotEqualTo(categorie2);
        categorie1.setId(null);
        assertThat(categorie1).isNotEqualTo(categorie2);
    }
}
