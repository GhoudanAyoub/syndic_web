<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="appartement">
        <h2 class="jh-entity-heading" data-cy="appartementDetailsHeading">
          <span v-text="$t('syndicWebApp.appartement.detail.title')">Appartement</span> {{ appartement.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.appartement.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ appartement.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.appartement.etage')">Etage</span>
          </dt>
          <dd>
            <span>{{ appartement.etage }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.appartement.surface')">Surface</span>
          </dt>
          <dd>
            <span>{{ appartement.surface }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.appartement.resident')">Resident</span>
          </dt>
          <dd>
            <div v-if="appartement.resident">
              <router-link :to="{ name: 'ResidentView', params: { residentId: appartement.resident.id } }">{{
                appartement.resident.nom + ' ' + appartement.resident.prenom
              }}</router-link>
            </div>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.appartement.immeuble')">Immeuble</span>
          </dt>
          <dd>
            <div v-if="appartement.immeuble">
              <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: appartement.immeuble.id } }">{{
                appartement.immeuble.libelle
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link
          v-if="appartement.id"
          :to="{ name: 'AppartementEdit', params: { appartementId: appartement.id } }"
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

<script lang="ts" src="./appartement-details.component.ts"></script>
