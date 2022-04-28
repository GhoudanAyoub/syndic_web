<template>
  <div>
    <h2 id="page-heading" data-cy="ResidentHeading">
      <span v-text="$t('syndicWebApp.resident.home.title')" id="resident-heading">Residents</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.resident.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'ResidentCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-resident"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.resident.home.createLabel')"> Create a new Resident </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && residents && residents.length === 0">
      <span v-text="$t('syndicWebApp.resident.home.notFound')">No residents found</span>
    </div>
    <div class="table-responsive" v-if="residents && residents.length > 0">
      <table class="table table-striped" aria-describedby="residents">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.email')">Email</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.motPasse')">Mot Passe</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.nom')">Nom</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.prenom')">Prenom</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.adresse')">Adresse</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.resident.tel')">Tel</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="resident in residents" :key="resident.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'ResidentView', params: { residentId: resident.id } }">{{ resident.id }}</router-link>
            </td>
            <td>{{ resident.email }}</td>
            <td>{{ resident.motPasse }}</td>
            <td>{{ resident.nom }}</td>
            <td>{{ resident.prenom }}</td>
            <td>{{ resident.adresse }}</td>
            <td>{{ resident.tel }}</td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'ResidentView', params: { residentId: resident.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'ResidentEdit', params: { residentId: resident.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(resident)"
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
        ><span id="syndicWebApp.resident.delete.question" data-cy="residentDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-resident-heading" v-text="$t('syndicWebApp.resident.delete.question', { id: removeId })">
          Are you sure you want to delete this Resident?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-resident"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeResident()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./resident.component.ts"></script>
