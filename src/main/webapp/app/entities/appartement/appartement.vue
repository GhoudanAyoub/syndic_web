<template>
  <div>
    <h2 id="page-heading" data-cy="AppartementHeading">
      <span v-text="$t('syndicWebApp.appartement.home.title')" id="appartement-heading">Appartements</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.appartement.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'AppartementCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-appartement"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.appartement.home.createLabel')"> Create a new Appartement </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && appartements && appartements.length === 0">
      <span v-text="$t('syndicWebApp.appartement.home.notFound')">No appartements found</span>
    </div>
    <div class="table-responsive" v-if="appartements && appartements.length > 0">
      <table class="table table-striped" aria-describedby="appartements">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.appartement.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.appartement.etage')">Etage</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.appartement.surface')">Surface</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.appartement.resident')">Resident</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.appartement.immeuble')">Immeuble</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="appartement in appartements" :key="appartement.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'AppartementView', params: { appartementId: appartement.id } }">{{ appartement.id }}</router-link>
            </td>
            <td>{{ appartement.numero }}</td>
            <td>{{ appartement.etage }}</td>
            <td>{{ appartement.surface }}</td>
            <td>
              <div v-if="appartement.resident">
                <router-link :to="{ name: 'ResidentView', params: { residentId: appartement.resident.id } }">{{
                  appartement.resident.nom + ' ' + appartement.resident.prenom
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="appartement.immeuble">
                <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: appartement.immeuble.id } }">{{
                  appartement.immeuble.libelle
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'AppartementView', params: { appartementId: appartement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'AppartementEdit', params: { appartementId: appartement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(appartement)"
                  variant="danger"
                  class="btn btn-sm"
                  data-cy="entityDeleteButton"
                  v-b-modal.removeEntity
                >
                  <font-awesome-icon icon="times"></font-awesome-icon>
                  <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                </b-button>
              </div>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
    <b-modal ref="removeEntity" id="removeEntity">
      <span slot="modal-title"
        ><span id="syndicWebApp.appartement.delete.question" data-cy="appartementDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-appartement-heading" v-text="$t('syndicWebApp.appartement.delete.question', { id: removeId })">
          Are you sure you want to delete this Appartement?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-appartement"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeAppartement()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./appartement.component.ts"></script>
