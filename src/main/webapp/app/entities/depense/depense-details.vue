<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="depense">
        <h2 class="jh-entity-heading" data-cy="depenseDetailsHeading">
          <span v-text="$t('syndicWebApp.depense.detail.title')">Depense</span> {{ depense.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.depense.montant')">Montant</span>
          </dt>
          <dd>
            <span>{{ depense.montant }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.depense.date')">Date</span>
          </dt>
          <dd>
            <span>{{ depense.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.depense.description')">Description</span>
          </dt>
          <dd>
            <span>{{ depense.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.depense.immeuble')">Immeuble</span>
          </dt>
          <dd>
            <div v-if="depense.immeuble">
              <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: depense.immeuble.id } }">{{
                depense.immeuble.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="depense.id" :to="{ name: 'DepenseEdit', params: { depenseId: depense.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./depense-details.component.ts"></script>
