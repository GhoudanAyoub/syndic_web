<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.depense.home.createOrEditLabel"
          data-cy="DepenseCreateUpdateHeading"
          v-text="$t('syndicWebApp.depense.home.createOrEditLabel')"
        >
          Create or edit a Depense
        </h2>
        <div>
          <div class="form-group" v-if="depense.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="depense.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.depense.montant')" for="depense-montant">Montant</label>
            <input
              type="number"
              class="form-control"
              name="montant"
              id="depense-montant"
              data-cy="montant"
              :class="{ valid: !$v.depense.montant.$invalid, invalid: $v.depense.montant.$invalid }"
              v-model.number="$v.depense.montant.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.depense.date')" for="depense-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="depense-date"
                  v-model="$v.depense.date.$model"
                  name="date"
                  class="form-control"
                  :locale="currentLanguage"
                  button-only
                  today-button
                  reset-button
                  close-button
                >
                </b-form-datepicker>
              </b-input-group-prepend>
              <b-form-input
                id="depense-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.depense.date.$invalid, invalid: $v.depense.date.$invalid }"
                v-model="$v.depense.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.depense.description')" for="depense-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="depense-description"
              data-cy="description"
              :class="{ valid: !$v.depense.description.$invalid, invalid: $v.depense.description.$invalid }"
              v-model="$v.depense.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.depense.immeuble')" for="depense-immeuble">Immeuble</label>
            <select class="form-control" id="depense-immeuble" data-cy="immeuble" name="immeuble" v-model="depense.immeuble">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="depense.immeuble && immeubleOption.id === depense.immeuble.id ? depense.immeuble : immeubleOption"
                v-for="immeubleOption in immeubles"
                :key="immeubleOption.id"
              >
                {{ immeubleOption.libelle }}
              </option>
            </select>
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
            :disabled="$v.depense.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./depense-update.component.ts"></script>
