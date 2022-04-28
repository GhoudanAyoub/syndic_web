<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.appartement.home.createOrEditLabel"
          data-cy="AppartementCreateUpdateHeading"
          v-text="$t('syndicWebApp.appartement.home.createOrEditLabel')"
        >
          Create or edit a Appartement
        </h2>
        <div>
          <div class="form-group" v-if="appartement.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="appartement.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.appartement.numero')" for="appartement-numero">Numero</label>
            <input
              type="number"
              class="form-control"
              name="numero"
              id="appartement-numero"
              data-cy="numero"
              :class="{ valid: !$v.appartement.numero.$invalid, invalid: $v.appartement.numero.$invalid }"
              v-model.number="$v.appartement.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.appartement.etage')" for="appartement-etage">Etage</label>
            <input
              type="number"
              class="form-control"
              name="etage"
              id="appartement-etage"
              data-cy="etage"
              :class="{ valid: !$v.appartement.etage.$invalid, invalid: $v.appartement.etage.$invalid }"
              v-model.number="$v.appartement.etage.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.appartement.surface')" for="appartement-surface">Surface</label>
            <input
              type="number"
              class="form-control"
              name="surface"
              id="appartement-surface"
              data-cy="surface"
              :class="{ valid: !$v.appartement.surface.$invalid, invalid: $v.appartement.surface.$invalid }"
              v-model.number="$v.appartement.surface.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.appartement.resident')" for="appartement-resident">Resident</label>
            <select class="form-control" id="appartement-resident" data-cy="resident" name="resident" v-model="appartement.resident">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="appartement.resident && residentOption.id === appartement.resident.id ? appartement.resident : residentOption"
                v-for="residentOption in residents"
                :key="residentOption.id"
              >
                {{ residentOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.appartement.immeuble')" for="appartement-immeuble">Immeuble</label>
            <select class="form-control" id="appartement-immeuble" data-cy="immeuble" name="immeuble" v-model="appartement.immeuble">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="appartement.immeuble && immeubleOption.id === appartement.immeuble.id ? appartement.immeuble : immeubleOption"
                v-for="immeubleOption in immeubles"
                :key="immeubleOption.id"
              >
                {{ immeubleOption.id }}
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
            :disabled="$v.appartement.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./appartement-update.component.ts"></script>
