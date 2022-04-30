<template>
  <div>
    <h2 id="page-heading" data-cy="PayementHeading">
      <span v-text="$t('syndicWebApp.payement.home.title')" id="payement-heading">Payements</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.payement.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'PayementCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-payement"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.payement.home.createLabel')"> Create a new Payement </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && payements && payements.length === 0">
      <span v-text="$t('syndicWebApp.payement.home.notFound')">No payements found</span>
    </div>
    <div class="table-responsive" v-if="payements && payements.length > 0">
      <table class="table table-striped" aria-describedby="payements">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.payement.montant')">Montant</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.payement.date')">Date</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.payement.description')">Description</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.payement.appartement')">Appartement</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="payement in payements" :key="payement.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'PayementView', params: { payementId: payement.id } }">{{ payement.id }}</router-link>
            </td>
            <td>{{ payement.montant }}</td>
            <td>{{ payement.date }}</td>
            <td>{{ payement.description }}</td>
            <td>
              <div v-if="payement.appartement">
                <router-link :to="{ name: 'AppartementView', params: { appartementId: payement.appartement.id } }">{{
                  payement.appartement.numero
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'PayementView', params: { payementId: payement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'PayementEdit', params: { payementId: payement.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(payement)"
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
        ><span id="syndicWebApp.payement.delete.question" data-cy="payementDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-payement-heading" v-text="$t('syndicWebApp.payement.delete.question', { id: removeId })">
          Are you sure you want to delete this Payement?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-payement"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removePayement()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./payement.component.ts"></script>
