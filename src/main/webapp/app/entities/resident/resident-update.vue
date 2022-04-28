<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.resident.home.createOrEditLabel"
          data-cy="ResidentCreateUpdateHeading"
          v-text="$t('syndicWebApp.resident.home.createOrEditLabel')"
        >
          Create or edit a Resident
        </h2>
        <div>
          <div class="form-group" v-if="resident.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="resident.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.email')" for="resident-email">Email</label>
            <input
              type="text"
              class="form-control"
              name="email"
              id="resident-email"
              data-cy="email"
              :class="{ valid: !$v.resident.email.$invalid, invalid: $v.resident.email.$invalid }"
              v-model="$v.resident.email.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.motPasse')" for="resident-motPasse">Mot Passe</label>
            <input
              type="text"
              class="form-control"
              name="motPasse"
              id="resident-motPasse"
              data-cy="motPasse"
              :class="{ valid: !$v.resident.motPasse.$invalid, invalid: $v.resident.motPasse.$invalid }"
              v-model="$v.resident.motPasse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.nom')" for="resident-nom">Nom</label>
            <input
              type="text"
              class="form-control"
              name="nom"
              id="resident-nom"
              data-cy="nom"
              :class="{ valid: !$v.resident.nom.$invalid, invalid: $v.resident.nom.$invalid }"
              v-model="$v.resident.nom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.prenom')" for="resident-prenom">Prenom</label>
            <input
              type="text"
              class="form-control"
              name="prenom"
              id="resident-prenom"
              data-cy="prenom"
              :class="{ valid: !$v.resident.prenom.$invalid, invalid: $v.resident.prenom.$invalid }"
              v-model="$v.resident.prenom.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.adresse')" for="resident-adresse">Adresse</label>
            <input
              type="text"
              class="form-control"
              name="adresse"
              id="resident-adresse"
              data-cy="adresse"
              :class="{ valid: !$v.resident.adresse.$invalid, invalid: $v.resident.adresse.$invalid }"
              v-model="$v.resident.adresse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.resident.tel')" for="resident-tel">Tel</label>
            <input
              type="text"
              class="form-control"
              name="tel"
              id="resident-tel"
              data-cy="tel"
              :class="{ valid: !$v.resident.tel.$invalid, invalid: $v.resident.tel.$invalid }"
              v-model="$v.resident.tel.$model"
            />
          </div>
        </div>
        <div>
          <button type="button" id="cancel-save" data-cy="entityCreateCancelButton" class="btn btn-secondary" v-on:click="previousState()">
            <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
          </button>
          <button
            type="submit"
            id="save-entity"
            data-cy="entityCreateSaveButton"
            :disabled="$v.resident.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./resident-update.component.ts"></script>
