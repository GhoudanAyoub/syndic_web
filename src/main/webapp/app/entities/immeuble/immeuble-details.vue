<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="immeuble">
        <h2 class="jh-entity-heading" data-cy="immeubleDetailsHeading">
          <span v-text="$t('syndicWebApp.immeuble.detail.title')">Immeuble</span> {{ immeuble.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.libelle')">Libelle</span>
          </dt>
          <dd>
            <span>{{ immeuble.libelle }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.adresse')">Adresse</span>
          </dt>
          <dd>
            <span>{{ immeuble.adresse }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.ville')">Ville</span>
          </dt>
          <dd>
            <span>{{ immeuble.ville }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.numero')">Numero</span>
          </dt>
          <dd>
            <span>{{ immeuble.numero }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.nbEtages')">Nb Etages</span>
          </dt>
          <dd>
            <span>{{ immeuble.nbEtages }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.photo')">Photo</span>
          </dt>
          <dd>
            <div v-if="immeuble.photo">
              <a v-on:click="openFile(immeuble.photoContentType, immeuble.photo)">
                <img
                  v-bind:src="'data:' + immeuble.photoContentType + ';base64,' + immeuble.photo"
                  style="max-width: 100%"
                  alt="immeuble image"
                />
              </a>
              {{ immeuble.photoContentType }}, {{ byteSize(immeuble.photo) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.immeuble.syndic')">Syndic</span>
          </dt>
          <dd>
            <div v-if="immeuble.syndic">
              <router-link :to="{ name: 'SyndicView', params: { syndicId: immeuble.syndic.id } }">{{ immeuble.syndic.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="immeuble.id" :to="{ name: 'ImmeubleEdit', params: { immeubleId: immeuble.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./immeuble-details.component.ts"></script>
