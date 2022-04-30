<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="resident">
        <h2 class="jh-entity-heading" data-cy="residentDetailsHeading">
          <span v-text="$t('syndicWebApp.resident.detail.title')">Resident</span> {{ resident.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.resident.email')">Email</span>
          </dt>
          <dd>
            <span>{{ resident.email }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.motPasse')">Mot Passe</span>
          </dt>
          <dd>
            <span>{{ resident.motPasse }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.nom')">Nom</span>
          </dt>
          <dd>
            <span>{{ resident.nom }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.prenom')">Prenom</span>
          </dt>
          <dd>
            <span>{{ resident.prenom }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.adresse')">Adresse</span>
          </dt>
          <dd>
            <span>{{ resident.adresse }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.tel')">Tel</span>
          </dt>
          <dd>
            <span>{{ resident.tel }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.resident.photo')">Photo</span>
          </dt>
          <dd>
            <div v-if="resident.photo">
              <a v-on:click="openFile(resident.photoContentType, resident.photo)">
                <img
                  v-bind:src="'data:' + resident.photoContentType + ';base64,' + resident.photo"
                  style="max-width: 100%"
                  alt="resident image"
                />
              </a>
              {{ resident.photoContentType }}, {{ byteSize(resident.photo) }}
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="resident.id" :to="{ name: 'ResidentEdit', params: { residentId: resident.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./resident-details.component.ts"></script>
