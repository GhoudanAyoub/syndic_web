<template>
  <div>
    <h2 id="page-heading" data-cy="CategorieHeading">
      <span v-text="$t('syndicWebApp.categorie.home.title')" id="categorie-heading">Categories</span>
      <div class="d-flex justify-content-end">
        <button class="btn btn-info mr-2" v-on:click="handleSyncList" :disabled="isFetching">
          <font-awesome-icon icon="sync" :spin="isFetching"></font-awesome-icon>
          <span v-text="$t('syndicWebApp.categorie.home.refreshListLabel')">Refresh List</span>
        </button>
        <router-link :to="{ name: 'CategorieCreate' }" custom v-slot="{ navigate }">
          <button
            @click="navigate"
            id="jh-create-entity"
            data-cy="entityCreateButton"
            class="btn btn-primary jh-create-entity create-categorie"
          >
            <font-awesome-icon icon="plus"></font-awesome-icon>
            <span v-text="$t('syndicWebApp.categorie.home.createLabel')"> Create a new Categorie </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div class="alert alert-warning" v-if="!isFetching && categories && categories.length === 0">
      <span v-text="$t('syndicWebApp.categorie.home.notFound')">No categories found</span>
    </div>
    <div class="table-responsive" v-if="categories && categories.length > 0">
      <table class="table table-striped" aria-describedby="categories">
        <thead>
          <tr>
            <th scope="row"><span v-text="$t('global.field.id')">ID</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.categorie.libelle')">Libelle</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.categorie.depense')">Depense</span></th>
            <th scope="row"><span v-text="$t('syndicWebApp.categorie.revenu')">Revenu</span></th>
            <th scope="row"></th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="categorie in categories" :key="categorie.id" data-cy="entityTable">
            <td>
              <router-link :to="{ name: 'CategorieView', params: { categorieId: categorie.id } }">{{ categorie.id }}</router-link>
            </td>
            <td>{{ categorie.libelle }}</td>
            <td>
              <div v-if="categorie.depense">
                <router-link :to="{ name: 'DepenseView', params: { depenseId: categorie.depense.id } }">{{
                  categorie.depense.description
                }}</router-link>
              </div>
            </td>
            <td>
              <div v-if="categorie.revenu">
                <router-link :to="{ name: 'RevenuView', params: { revenuId: categorie.revenu.id } }">{{
                  categorie.revenu.description
                }}</router-link>
              </div>
            </td>
            <td class="text-right">
              <div class="btn-group">
                <router-link :to="{ name: 'CategorieView', params: { categorieId: categorie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-info btn-sm details" data-cy="entityDetailsButton">
                    <font-awesome-icon icon="eye"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                  </button>
                </router-link>
                <router-link :to="{ name: 'CategorieEdit', params: { categorieId: categorie.id } }" custom v-slot="{ navigate }">
                  <button @click="navigate" class="btn btn-primary btn-sm edit" data-cy="entityEditButton">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                    <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                  </button>
                </router-link>
                <b-button
                  v-on:click="prepareRemove(categorie)"
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
        ><span id="syndicWebApp.categorie.delete.question" data-cy="categorieDeleteDialogHeading" v-text="$t('entity.delete.title')"
          >Confirm delete operation</span
        ></span
      >
      <div class="modal-body">
        <p id="jhi-delete-categorie-heading" v-text="$t('syndicWebApp.categorie.delete.question', { id: removeId })">
          Are you sure you want to delete this Categorie?
        </p>
      </div>
      <div slot="modal-footer">
        <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
        <button
          type="button"
          class="btn btn-primary"
          id="jhi-confirm-delete-categorie"
          data-cy="entityConfirmDeleteButton"
          v-text="$t('entity.action.delete')"
          v-on:click="removeCategorie()"
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang="ts" src="./categorie.component.ts"></script>
