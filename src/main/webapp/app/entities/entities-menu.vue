<template>
  <div>
    <b-dropdown-item to="/resident">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.resident')">Resident</span>
    </b-dropdown-item>
    <b-dropdown-item to="/syndic">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.syndic')">Syndic</span>
    </b-dropdown-item>
    <b-dropdown-item to="/immeuble">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.immeuble')">Immeuble</span>
    </b-dropdown-item>
    <b-dropdown-item to="/appartement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.appartement')">Appartement</span>
    </b-dropdown-item>
    <b-dropdown-item to="/revenu">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.revenu')">Revenu</span>
    </b-dropdown-item>
    <b-dropdown-item to="/depense">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.depense')">Depense</span>
    </b-dropdown-item>
    <b-dropdown-item to="/categorie">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.categorie')">Categorie</span>
    </b-dropdown-item>
    <b-dropdown-item to="/payement">
      <font-awesome-icon icon="asterisk" />
      <span v-text="$t('global.menu.entities.payement')">Payement</span>
    </b-dropdown-item>
    <!-- jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here -->
  </div>
</template>

<script lang="ts" src="./entities-menu.component.ts"></script>
