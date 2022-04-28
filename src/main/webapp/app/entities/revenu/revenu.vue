<template>
  <div>
    <h2 id="page-heading" data-cy="RevenuHeading">
      <span v-text="$t('syndicWebApp.revenu.home.title')" id="revenu-heading">Revenus</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.revenu.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'RevenuCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-revenu"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.revenu.home.createLabel')"> Create a new Revenu </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && revenus && revenus.length === 0">
      <span v-text="$t('syndicWebApp.revenu.home.notFound')">No revenus found</span>
    </div>
    <div class="table-responsive" v-if="revenus && revenus.length > 0">
      <table class="table table-striped" aria-describedby="revenus">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.revenu.montant')">Montant</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.revenu.date')">Date</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.revenu.description')">Description</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.revenu.immeuble')">Immeuble</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="revenu in revenus" :key="revenu.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'RevenuView', params: { revenuId: revenu.id } }">{{ revenu.id }}</router-link>
            </td>
            <td>{{ revenu.montant }}</td>
            <td>{{ revenu.date }}</td>
            <td>{{ revenu.description }}</td>
            <td>
              <div v-if="revenu.immeuble">
                <router-link :to="{ name: 'ImmeubleView', params: { immeubleId: revenu.immeuble.id } }">{{
                  revenu.immeuble.id
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'RevenuView', params: { revenuId: revenu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'RevenuEdit', params: { revenuId: revenu.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(revenu)"
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
        ><span id="syndicWebApp.revenu.delete.question" data-cy="revenuDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-revenu-heading" v-text="$t('syndicWebApp.revenu.delete.question', { id: removeId })">
          Are you sure you want to delete this Revenu?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-revenu"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeRevenu()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./revenu.component.ts"></script>
