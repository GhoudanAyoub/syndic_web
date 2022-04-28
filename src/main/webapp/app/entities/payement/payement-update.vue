<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.payement.home.createOrEditLabel"
          data-cy="PayementCreateUpdateHeading"
          v-text="$t('syndicWebApp.payement.home.createOrEditLabel')"
        >
          Create or edit a Payement
        </h2>
        <div>
          <div class="form-group" v-if="payement.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="payement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.payement.montant')" for="payement-montant">Montant</label>
            <input
              type="number"
              class="form-control"
              name="montant"
              id="payement-montant"
              data-cy="montant"
              :class="{ valid: !$v.payement.montant.$invalid, invalid: $v.payement.montant.$invalid }"
              v-model.number="$v.payement.montant.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.payement.date')" for="payement-date">Date</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="payement-date"
                  v-model="$v.payement.date.$model"
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
                id="payement-date"
                data-cy="date"
                type="text"
                class="form-control"
                name="date"
                :class="{ valid: !$v.payement.date.$invalid, invalid: $v.payement.date.$invalid }"
                v-model="$v.payement.date.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.payement.description')" for="payement-description"
              >Description</label
            >
            <input
              type="text"
              class="form-control"
              name="description"
              id="payement-description"
              data-cy="description"
              :class="{ valid: !$v.payement.description.$invalid, invalid: $v.payement.description.$invalid }"
              v-model="$v.payement.description.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.payement.appartement')" for="payement-appartement"
              >Appartement</label
            >
            <select class="form-control" id="payement-appartement" data-cy="appartement" name="appartement" v-model="payement.appartement">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="
                  payement.appartement && appartementOption.id === payement.appartement.id ? payement.appartement : appartementOption
                "
                v-for="appartementOption in appartements"
                :key="appartementOption.id"
              >
                {{ appartementOption.id }}
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
            :disabled="$v.payement.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./payement-update.component.ts"></script>
