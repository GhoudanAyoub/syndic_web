<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.revenu.home.createOrEditLabel"
          data-cy="RevenuCreateUpdateHeading"
          v-text="$t('syndicWebApp.revenu.home.createOrEditLabel')"
        >
          Create or edit a Revenu
        </h2>
        <div>
          <div class="form-group" v-if="revenu.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="revenu.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.revenu.montant')" for="revenu-montant">Montant</label>
            <input
              type="number"
              class="form-control"
              name="montant"
              id="revenu-montant"
              data-cy="montant"
              :class="{ valid: !$v.revenu.montant.$invalid, invalid: $v.revenu.montant.$invalid }"
              v-model.number="$v.revenu.montant.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.revenu.date')" for="revenu-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="revenu-date"
                  v-model="$v.revenu.date.$model"
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
                id="revenu-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.revenu.date.$invalid, invalid: $v.revenu.date.$invalid }"
                v-model="$v.revenu.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.revenu.description')" for="revenu-description">Description</label>
            <input
              type="text"
              class="form-control"
              name="description"
              id="revenu-description"
              data-cy="description"
              :class="{ valid: !$v.revenu.description.$invalid, invalid: $v.revenu.description.$invalid }"
              v-model="$v.revenu.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.revenu.immeuble')" for="revenu-immeuble">Immeuble</label>
            <select class="form-control" id="revenu-immeuble" data-cy="immeuble" name="immeuble" v-model="revenu.immeuble">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="revenu.immeuble && immeubleOption.id === revenu.immeuble.id ? revenu.immeuble : immeubleOption"
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
            :disabled="$v.revenu.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./revenu-update.component.ts"></script>
