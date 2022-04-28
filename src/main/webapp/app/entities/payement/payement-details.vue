<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <div v-if="payement">
        <h2 class="jh-entity-heading" data-cy="payementDetailsHeading">
          <span v-text="$t('syndicWebApp.payement.detail.title')">Payement</span> {{ payement.id }}
        </h2>
        <dl class="row jh-entity-details">
          <dt>
            <span v-text="$t('syndicWebApp.payement.montant')">Montant</span>
          </dt>
          <dd>
            <span>{{ payement.montant }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.payement.date')">Date</span>
          </dt>
          <dd>
            <span>{{ payement.date }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.payement.description')">Description</span>
          </dt>
          <dd>
            <span>{{ payement.description }}</span>
          </dd>
          <dt>
            <span v-text="$t('syndicWebApp.payement.appartement')">Appartement</span>
          </dt>
          <dd>
            <div v-if="payement.appartement">
              <router-link :to="{ name: 'AppartementView', params: { appartementId: payement.appartement.id } }">{{
                payement.appartement.id
              }}</router-link>
            </div>
          </dd>
        </dl>
        <button type="submit" v-on:click.prevent="previousState()" class="btn btn-info" data-cy="entityDetailsBackButton">
          <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link v-if="payement.id" :to="{ name: 'PayementEdit', params: { payementId: payement.id } }" custom v-slot="{ navigate }">
          <button @click="navigate" class="btn btn-primary">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang="ts" src="./payement-details.component.ts"></script>
