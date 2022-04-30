<template>
  <div>
    <h2 id="page-heading" data-cy="SyndicHeading">
      <span v-text="$t('syndicWebApp.syndic.home.title')" id="syndic-heading">Syndics</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.syndic.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'SyndicCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-syndic"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.syndic.home.createLabel')"> Create a new Syndic </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && syndics && syndics.length === 0">
      <span v-text="$t('syndicWebApp.syndic.home.notFound')">No syndics found</span>
    </div>
    <div class="table-responsive" v-if="syndics && syndics.length > 0">
      <table class="table table-striped" aria-describedby="syndics">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.syndic.adresse')">Adresse</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.syndic.tel')">Tel</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.syndic.dateTravail')">Date Travail</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.syndic.photo')">Photo</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.syndic.user')">User</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="syndic in syndics" :key="syndic.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'SyndicView', params: { syndicId: syndic.id } }">{{ syndic.id }}</router-link>
            </td>
            <td>{{ syndic.adresse }}</td>
            <td>{{ syndic.tel }}</td>
            <td>{{ syndic.dateTravail }}</td>
            <td>
              <a v-if="syndic.photo" v-on:click="openFile(syndic.photoContentType, syndic.photo)">
                <img
                  v-bind:src="'data:' + syndic.photoContentType + ';base64,' + syndic.photo"
                  style="max-height: 30px"
                  alt="syndic image"
                />
              </a>
              <span v-if="syndic.photo">{{ syndic.photoContentType }}, {{ byteSize(syndic.photo) }}</span>
            </td>
            <td>
              {{ syndic.user ? syndic.user.login : '' }}
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'SyndicView', params: { syndicId: syndic.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'SyndicEdit', params: { syndicId: syndic.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(syndic)"
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
        ><span id="syndicWebApp.syndic.delete.question" data-cy="syndicDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-syndic-heading" v-text="$t('syndicWebApp.syndic.delete.question', { id: removeId })">
          Are you sure you want to delete this Syndic?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-syndic"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeSyndic()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./syndic.component.ts"></script>
