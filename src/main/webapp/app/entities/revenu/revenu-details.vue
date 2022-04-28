<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="revenu">
        <h2 class="jh-entity-heading" data-cy="revenuDetailsHeading">
          <span v-text="$t('syndicWebApp.revenu.detail.title')">Revenu</span> {{ revenu.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.revenu.montant')">Montant</span>
          </dt>
          <dd>
            <span>{{ revenu.montant }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.revenu.date')">Date</span>
          </dt>
          <dd>
            <span>{{ revenu.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.revenu.description')">Description</span>
          </dt>
          <dd>
            <span>{{ revenu.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.revenu.immeuble')">Immeuble</span>
          </dt>
          <dd>
            <div v-if="revenu.immeuble">
              <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: revenu.immeuble.id } }">{{ revenu.immeuble.id }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="revenu.id" :to="{ name: 'RevenuEdit', params: { revenuId: revenu.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./revenu-details.component.ts"></script>
