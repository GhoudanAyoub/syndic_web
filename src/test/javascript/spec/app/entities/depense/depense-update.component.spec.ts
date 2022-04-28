/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DepenseUpdateComponent from '@/entities/depense/depense-update.vue';
import DepenseClass from '@/entities/depense/depense-update.component';
import DepenseService from '@/entities/depense/depense.service';

import CategorieService from '@/entities/categorie/categorie.service';

import ImmeubleService from '@/entities/immeuble/immeuble.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.use(ToastPlugin);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Depense Management Update Component', () => {
    let wrapper: Wrapper<DepenseClass>;
    let comp: DepenseClass;
    let depenseServiceStub: SinonStubbedInstance<DepenseService>;

    beforeEach(() => {
      depenseServiceStub = sinon.createStubInstance<DepenseService>(DepenseService);

      wrapper = shallowMount<DepenseClass>(DepenseUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          depenseService: () => depenseServiceStub,
          alertService: () => new AlertService(),

          categorieService: () =>
            sinon.createStubInstance<CategorieService>(CategorieService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          immeubleService: () =>
            sinon.createStubInstance<ImmeubleService>(ImmeubleService, {
              retrieve: sinon.stub().resolves({}),
            } as any),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.depense = entity;
        depenseServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(depenseServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.depense = entity;
        depenseServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(depenseServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDepense = { id: 123 };
        depenseServiceStub.find.resolves(foundDepense);
        depenseServiceStub.retrieve.resolves([foundDepense]);

        // WHEN
        comp.beforeRouteEnter({ params: { depenseId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.depense).toBe(foundDepense);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
