<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.immeuble.home.createOrEditLabel"
          data-cy="ImmeubleCreateUpdateHeading"
          v-text="$t('syndicWebApp.immeuble.home.createOrEditLabel')"
        >
          Create or edit a Immeuble
        </h2>
        <div>
          <div class="form-group" v-if="immeuble.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="immeuble.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.libelle')" for="immeuble-libelle">Libelle</label>
            <input
              type="text"
              class="form-control"
              name="libelle"
              id="immeuble-libelle"
              data-cy="libelle"
              :class="{ valid: !$v.immeuble.libelle.$invalid, invalid: $v.immeuble.libelle.$invalid }"
              v-model="$v.immeuble.libelle.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.adresse')" for="immeuble-adresse">Adresse</label>
            <input
              type="text"
              class="form-control"
              name="adresse"
              id="immeuble-adresse"
              data-cy="adresse"
              :class="{ valid: !$v.immeuble.adresse.$invalid, invalid: $v.immeuble.adresse.$invalid }"
              v-model="$v.immeuble.adresse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.ville')" for="immeuble-ville">Ville</label>
            <input
              type="text"
              class="form-control"
              name="ville"
              id="immeuble-ville"
              data-cy="ville"
              :class="{ valid: !$v.immeuble.ville.$invalid, invalid: $v.immeuble.ville.$invalid }"
              v-model="$v.immeuble.ville.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.numero')" for="immeuble-numero">Numero</label>
            <input
              type="number"
              class="form-control"
              name="numero"
              id="immeuble-numero"
              data-cy="numero"
              :class="{ valid: !$v.immeuble.numero.$invalid, invalid: $v.immeuble.numero.$invalid }"
              v-model.number="$v.immeuble.numero.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.nbEtages')" for="immeuble-nbEtages">Nb Etages</label>
            <input
              type="number"
              class="form-control"
              name="nbEtages"
              id="immeuble-nbEtages"
              data-cy="nbEtages"
              :class="{ valid: !$v.immeuble.nbEtages.$invalid, invalid: $v.immeuble.nbEtages.$invalid }"
              v-model.number="$v.immeuble.nbEtages.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.photo')" for="immeuble-photo">Photo</label>
            <div>
              <img
                v-bind:src="'data:' + immeuble.photoContentType + ';base64,' + immeuble.photo"
                style="max-height: 100px"
                v-if="immeuble.photo"
                alt="immeuble image"
              />
              <div v-if="immeuble.photo" class="form-text text-danger clearfix">
                <span class="pull-left">{{ immeuble.photoContentType }}, {{ byteSize(immeuble.photo) }}</span>
                <button
                  type="button"
                  v-on:click="clearInputImage('photo', 'photoContentType', 'file_photo')"
                  class="btn btn-secondary btn-xs pull-right"
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                </button>
              </div>
              <input
                type="file"
                ref="file_photo"
                id="file_photo"
                data-cy="photo"
                v-on:change="setFileData($event, immeuble, 'photo', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="photo"
              id="immeuble-photo"
              data-cy="photo"
              :class="{ valid: !$v.immeuble.photo.$invalid, invalid: $v.immeuble.photo.$invalid }"
              v-model="$v.immeuble.photo.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="photoContentType"
              id="immeuble-photoContentType"
              v-model="immeuble.photoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.immeuble.syndic')" for="immeuble-syndic">Syndic</label>
            <select class="form-control" id="immeuble-syndic" data-cy="syndic" name="syndic" v-model="immeuble.syndic">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="immeuble.syndic && syndicOption.id === immeuble.syndic.id ? immeuble.syndic : syndicOption"
                v-for="syndicOption in syndics"
                :key="syndicOption.id"
              >
                {{ syndicOption.id }}
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
            :disabled="$v.immeuble.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./immeuble-update.component.ts"></script>
