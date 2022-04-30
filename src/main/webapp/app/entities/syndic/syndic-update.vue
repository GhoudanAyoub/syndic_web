<template>
  <div class="row justify-content-center">
    <div class="col-8">
      <form name="editForm" role="form" novalidate v-on:submit.prevent="save()">
        <h2
          id="syndicWebApp.syndic.home.createOrEditLabel"
          data-cy="SyndicCreateUpdateHeading"
          v-text="$t('syndicWebApp.syndic.home.createOrEditLabel')"
        >
          Create or edit a Syndic
        </h2>
        <div>
          <div class="form-group" v-if="syndic.id">
            <label for="id" v-text="$t('global.field.id')">ID</label>
            <input type="text" class="form-control" id="id" name="id" v-model="syndic.id" readonly />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.syndic.adresse')" for="syndic-adresse">Adresse</label>
            <input
              type="text"
              class="form-control"
              name="adresse"
              id="syndic-adresse"
              data-cy="adresse"
              :class="{ valid: !$v.syndic.adresse.$invalid, invalid: $v.syndic.adresse.$invalid }"
              v-model="$v.syndic.adresse.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.syndic.tel')" for="syndic-tel">Tel</label>
            <input
              type="text"
              class="form-control"
              name="tel"
              id="syndic-tel"
              data-cy="tel"
              :class="{ valid: !$v.syndic.tel.$invalid, invalid: $v.syndic.tel.$invalid }"
              v-model="$v.syndic.tel.$model"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.syndic.dateTravail')" for="syndic-dateTravail">Date Travail</label>
            <b-input-group class="mb-3">
              <b-input-group-prepend>
                <b-form-datepicker
                  aria-controls="syndic-dateTravail"
                  v-model="$v.syndic.dateTravail.$model"
                  name="dateTravail"
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
                id="syndic-dateTravail"
                data-cy="dateTravail"
                type="text"
                class="form-control"
                name="dateTravail"
                :class="{ valid: !$v.syndic.dateTravail.$invalid, invalid: $v.syndic.dateTravail.$invalid }"
                v-model="$v.syndic.dateTravail.$model"
              />
            </b-input-group>
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.syndic.photo')" for="syndic-photo">Photo</label>
            <div>
              <img
                v-bind:src="'data:' + syndic.photoContentType + ';base64,' + syndic.photo"
                style="max-height: 100px"
                v-if="syndic.photo"
                alt="syndic image"
              />
              <div v-if="syndic.photo" class="form-text text-danger clearfix">
                <span class="pull-left">{{ syndic.photoContentType }}, {{ byteSize(syndic.photo) }}</span>
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
                v-on:change="setFileData($event, syndic, 'photo', true)"
                accept="image/*"
                v-text="$t('entity.action.addimage')"
              />
            </div>
            <input
              type="hidden"
              class="form-control"
              name="photo"
              id="syndic-photo"
              data-cy="photo"
              :class="{ valid: !$v.syndic.photo.$invalid, invalid: $v.syndic.photo.$invalid }"
              v-model="$v.syndic.photo.$model"
            />
            <input
              type="hidden"
              class="form-control"
              name="photoContentType"
              id="syndic-photoContentType"
              v-model="syndic.photoContentType"
            />
          </div>
          <div class="form-group">
            <label class="form-control-label" v-text="$t('syndicWebApp.syndic.user')" for="syndic-user">User</label>
            <select class="form-control" id="syndic-user" data-cy="user" name="user" v-model="syndic.user">
              <option v-bind:value="null"></option>
              <option
                v-bind:value="syndic.user && userOption.id === syndic.user.id ? syndic.user : userOption"
                v-for="userOption in users"
                :key="userOption.id"
              >
                {{ userOption.login }}
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
            :disabled="$v.syndic.$invalid || isSaving"
            class="btn btn-primary"
          >
            <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
          </button>
        </div>
      </form>
    </div>
  </div>
</template>
<script lang="ts" src="./syndic-update.component.ts"></script>
