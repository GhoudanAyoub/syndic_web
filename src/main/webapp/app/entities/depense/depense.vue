<template>
  <div>
    <h2 id="page-heading" data-cy="DepenseHeading">
      <span v-text="$t('syndicWebApp.depense.home.title')" id="depense-heading">Depenses</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.depense.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'DepenseCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-depense"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.depense.home.createLabel')"> Create a new Depense </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && depenses && depenses.length === 0">
      <span v-text="$t('syndicWebApp.depense.home.notFound')">No depenses found</span>
    </div>
    <div class="table-responsive" v-if="depenses && depenses.length > 0">
      <table class="table table-striped" aria-describedby="depenses">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.depense.montant')">Montant</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.depense.date')">Date</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.depense.description')">Description</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.depense.immeuble')">Immeuble</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="depense in depenses" :key="depense.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'DepenseView', params: { depenseId: depense.id } }">{{ depense.id }}</router-link>
            </td>
            <td>{{ depense.montant }}</td>
            <td>{{ depense.date }}</td>
            <td>{{ depense.description }}</td>
            <td>
              <div v-if="depense.immeuble">
                <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: depense.immeuble.id } }">{{
                  depense.immeuble.libelle
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'DepenseView', params: { depenseId: depense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'DepenseEdit', params: { depenseId: depense.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(depense)"
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
        ><span id="syndicWebApp.depense.delete.question" data-cy="depenseDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-depense-heading" v-text="$t('syndicWebApp.depense.delete.question', { id: removeId })">
          Are you sure you want to delete this Depense?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-depense"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeDepense()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./depense.component.ts"></script>
