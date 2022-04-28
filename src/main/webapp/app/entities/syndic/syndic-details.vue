<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="syndic">
        <h2 class="jh-entity-heading" data-cy="syndicDetailsHeading">
          <span v-text="$t('syndicWebApp.syndic.detail.title')">Syndic</span> {{ syndic.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.syndic.adresse')">Adresse</span>
          </dt>
          <dd>
            <span>{{ syndic.adresse }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.syndic.tel')">Tel</span>
          </dt>
          <dd>
            <span>{{ syndic.tel }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.syndic.dateTravail')">Date Travail</span>
          </dt>
          <dd>
            <span>{{ syndic.dateTravail }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.syndic.photo')">Photo</span>
          </dt>
          <dd>
            <div v-if="syndic.photo">
              <a v-on:click="openFile(syndic.photoContentType, syndic.photo)">
                <img
                  v-bind:src="'data:' + syndic.photoContentType + ';base64,' + syndic.photo"
                  style="max-width: 100%"
                  alt="syndic image"
                />
              </a>
              {{ syndic.photoContentType }}, {{ byteSize(syndic.photo) }}
            </div>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.syndic.user')">User</span>
          </dt>
          <dd>
            {{ syndic.user ? syndic.user.id : '' }}
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="syndic.id" :to="{ name: 'SyndicEdit', params: { syndicId: syndic.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./syndic-details.component.ts"></script>
