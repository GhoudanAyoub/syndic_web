import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore
const Entities = () => import('@/entities/entities.vue');

const Resident = () => import('@/entities/resident/resident.vue');
const ResidentUpdate = () => import('@/entities/resident/resident-update.vue');
const ResidentDetails = () => import('@/entities/resident/resident-details.vue');

const Syndic = () => import('@/entities/syndic/syndic.vue');
const SyndicUpdate = () => import('@/entities/syndic/syndic-update.vue');
const SyndicDetails = () => import('@/entities/syndic/syndic-details.vue');

const Immeuble = () => import('@/entities/immeuble/immeuble.vue');
const ImmeubleUpdate = () => import('@/entities/immeuble/immeuble-update.vue');
const ImmeubleDetails = () => import('@/entities/immeuble/immeuble-details.vue');

const Appartement = () => import('@/entities/appartement/appartement.vue');
const AppartementUpdate = () => import('@/entities/appartement/appartement-update.vue');
const AppartementDetails = () => import('@/entities/appartement/appartement-details.vue');

const Revenu = () => import('@/entities/revenu/revenu.vue');
const RevenuUpdate = () => import('@/entities/revenu/revenu-update.vue');
const RevenuDetails = () => import('@/entities/revenu/revenu-details.vue');

const Depense = () => import('@/entities/depense/depense.vue');
const DepenseUpdate = () => import('@/entities/depense/depense-update.vue');
const DepenseDetails = () => import('@/entities/depense/depense-details.vue');

const Categorie = () => import('@/entities/categorie/categorie.vue');
const CategorieUpdate = () => import('@/entities/categorie/categorie-update.vue');
const CategorieDetails = () => import('@/entities/categorie/categorie-details.vue');

const Payement = () => import('@/entities/payement/payement.vue');
const PayementUpdate = () => import('@/entities/payement/payement-update.vue');
const PayementDetails = () => import('@/entities/payement/payement-details.vue');

// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default {
  path: '/',
  component: Entities,
  children: [
    {
      path: 'resident',
      name: 'Resident',
      component: Resident,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resident/new',
      name: 'ResidentCreate',
      component: ResidentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resident/:residentId/edit',
      name: 'ResidentEdit',
      component: ResidentUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'resident/:residentId/view',
      name: 'ResidentView',
      component: ResidentDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'syndic',
      name: 'Syndic',
      component: Syndic,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'syndic/new',
      name: 'SyndicCreate',
      component: SyndicUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'syndic/:syndicId/edit',
      name: 'SyndicEdit',
      component: SyndicUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'syndic/:syndicId/view',
      name: 'SyndicView',
      component: SyndicDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'immeuble',
      name: 'Immeuble',
      component: Immeuble,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'immeuble/new',
      name: 'ImmeubleCreate',
      component: ImmeubleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'immeuble/:immeubleId/edit',
      name: 'ImmeubleEdit',
      component: ImmeubleUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'immeuble/:immeubleId/view',
      name: 'ImmeubleView',
      component: ImmeubleDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartement',
      name: 'Appartement',
      component: Appartement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartement/new',
      name: 'AppartementCreate',
      component: AppartementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartement/:appartementId/edit',
      name: 'AppartementEdit',
      component: AppartementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'appartement/:appartementId/view',
      name: 'AppartementView',
      component: AppartementDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'revenu',
      name: 'Revenu',
      component: Revenu,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'revenu/new',
      name: 'RevenuCreate',
      component: RevenuUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'revenu/:revenuId/edit',
      name: 'RevenuEdit',
      component: RevenuUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'revenu/:revenuId/view',
      name: 'RevenuView',
      component: RevenuDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'depense',
      name: 'Depense',
      component: Depense,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'depense/new',
      name: 'DepenseCreate',
      component: DepenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'depense/:depenseId/edit',
      name: 'DepenseEdit',
      component: DepenseUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'depense/:depenseId/view',
      name: 'DepenseView',
      component: DepenseDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie',
      name: 'Categorie',
      component: Categorie,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie/new',
      name: 'CategorieCreate',
      component: CategorieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie/:categorieId/edit',
      name: 'CategorieEdit',
      component: CategorieUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'categorie/:categorieId/view',
      name: 'CategorieView',
      component: CategorieDetails,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payement',
      name: 'Payement',
      component: Payement,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payement/new',
      name: 'PayementCreate',
      component: PayementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payement/:payementId/edit',
      name: 'PayementEdit',
      component: PayementUpdate,
      meta: { authorities: [Authority.USER] },
    },
    {
      path: 'payement/:payementId/view',
      name: 'PayementView',
      component: PayementDetails,
      meta: { authorities: [Authority.USER] },
    },
    // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
  ],
};
