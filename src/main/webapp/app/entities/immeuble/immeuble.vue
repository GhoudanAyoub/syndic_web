<template>
  <div>
    <h2 id="page-heading" data-cy="ImmeubleHeading">
      <span v-text="$t('syndicWebApp.immeuble.home.title')" id="immeuble-heading">Immeubles</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.immeuble.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ImmeubleCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-immeuble"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.immeuble.home.createLabel')"> Create a new Immeuble </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && immeubles && immeubles.length === 0">
      <span v-text="$t('syndicWebApp.immeuble.home.notFound')">No immeubles found</span>
    </div>
    <div class="table-responsive" v-if="immeubles && immeubles.length > 0">
      <table class="table table-striped" aria-describedby="immeubles">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.adresse')">Adresse</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.ville')">Ville</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.numero')">Numero</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.nbEtages')">Nb Etages</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.photo')">Photo</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.immeuble.syndic')">Syndic</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="immeuble in immeubles" :key="immeuble.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: immeuble.id } }">{{ immeuble.id }}</router-link>
            </td>
            <td>{{ immeuble.libelle }}</td>
            <td>{{ immeuble.adresse }}</td>
            <td>{{ immeuble.ville }}</td>
            <td>{{ immeuble.numero }}</td>
            <td>{{ immeuble.nbEtages }}</td>
            <td>
              <a v-if="immeuble.photo" v-on:click="openFile(immeuble.photoContentType, immeuble.photo)">
                <img
                  v-bind:src="'data:' + immeuble.photoContentType + ';base64,' + immeuble.photo"
                  style="max-height: 30px"
                  alt="immeuble image"
                />
              </a>
              <span v-if="immeuble.photo">{{ immeuble.photoContentType }}, {{ byteSize(immeuble.photo) }}</span>
            </td>
            <td>
              <div v-if="immeuble.syndic">
                <router-link :to="{ name: 'SyndicView', params: { syndicId: immeuble.syndic.id } }">{{ immeuble.syndic.id }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: immeuble.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ImmeubleEdit', params: { immeubleId: immeuble.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(immeuble)"
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
        ><span id="syndicWebApp.immeuble.delete.question" data-cy="immeubleDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-immeuble-heading" v-text="$t('syndicWebApp.immeuble.delete.question', { id: removeId })">
          Are you sure you want to delete this Immeuble?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-immeuble"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeImmeuble()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./immeuble.component.ts"></script>
