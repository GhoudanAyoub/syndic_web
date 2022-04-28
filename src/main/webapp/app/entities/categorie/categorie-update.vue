<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.categorie.home.createOrEditLabel"
          data-cy="CategorieCreateUpdateHeading"
          v-text="$t('syndicWebApp.categorie.home.createOrEditLabel')"
        >
          Create or edit a Categorie
        </h2>
        <div>
          <div class="form-group" v-if="categorie.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="categorie.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.categorie.libelle')" for="categorie-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="categorie-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.categorie.libelle.$invalid, invalid: $v.categorie.libelle.$invalid }"
              v-model="$v.categorie.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.categorie.depense')" for="categorie-depense">Depense</label>
            <select class="form-control" id="categorie-depense" data-cy="depense" name="depense" v-model="categorie.depense">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="categorie.depense && depenseOption.id === categorie.depense.id ? categorie.depense : depenseOption"
                v-for="depenseOption in depenses"
                :key="depenseOption.id"
              >
                {{ depenseOption.id }}
              </option>
            </select>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.categorie.revenu')" for="categorie-revenu">Revenu</label>
            <select class="form-control" id="categorie-revenu" data-cy="revenu" name="revenu" v-model="categorie.revenu">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="categorie.revenu && revenuOption.id === categorie.revenu.id ? categorie.revenu : revenuOption"
                v-for="revenuOption in revenus"
                :key="revenuOption.id"
              >
                {{ revenuOption.id }}
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
            :disabled="$v.categorie.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./categorie-update.component.ts"></script>
