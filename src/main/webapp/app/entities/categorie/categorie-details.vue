<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="categorie">
        <h2 class="jh-entity-heading" data-cy="categorieDetailsHeading">
          <span v-text="$t('syndicWebApp.categorie.detail.title')">Categorie</span> {{ categorie.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.categorie.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ categorie.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.categorie.depense')">Depense</span>
          </dt>
          <dd>
            <div v-if="categorie.depense">
              <router-link :to="{ name: 'DepenseView', params: { depenseId: categorie.depense.id } }">{{
                categorie.depense.id
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.categorie.revenu')">Revenu</span>
          </dt>
          <dd>
            <div v-if="categorie.revenu">
              <router-link :to="{ name: 'RevenuView', params: { revenuId: categorie.revenu.id } }">{{ categorie.revenu.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="categorie.id"
          :to="{ name: 'CategorieEdit', params: { categorieId: categorie.id } }"
          custom
          v-slot="{ navigate }"
        >
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./categorie-details.component.ts"></script>
