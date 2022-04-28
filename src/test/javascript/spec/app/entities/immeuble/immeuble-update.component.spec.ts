/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ImmeubleUpdateComponent from '@/entities/immeuble/immeuble-update.vue';
import ImmeubleClass from '@/entities/immeuble/immeuble-update.component';
import ImmeubleService from '@/entities/immeuble/immeuble.service';

import AppartementService from '@/entities/appartement/appartement.service';

import SyndicService from '@/entities/syndic/syndic.service';

import DepenseService from '@/entities/depense/depense.service';

import RevenuService from '@/entities/revenu/revenu.service';
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
  describe('Immeuble Management Update Component', () => {
    let wrapper: Wrapper<ImmeubleClass>;
    let comp: ImmeubleClass;
    let immeubleServiceStub: SinonStubbedInstance<ImmeubleService>;

    beforeEach(() => {
      immeubleServiceStub = sinon.createStubInstance<ImmeubleService>(ImmeubleService);

      wrapper = shallowMount<ImmeubleClass>(ImmeubleUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          immeubleService: () => immeubleServiceStub,
          alertService: () => new AlertService(),

          appartementService: () =>
            sinon.createStubInstance<AppartementService>(AppartementService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          syndicService: () =>
            sinon.createStubInstance<SyndicService>(SyndicService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          depenseService: () =>
            sinon.createStubInstance<DepenseService>(DepenseService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          revenuService: () =>
            sinon.createStubInstance<RevenuService>(RevenuService, {
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
        comp.immeuble = entity;
        immeubleServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(immeubleServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.immeuble = entity;
        immeubleServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(immeubleServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundImmeuble = { id: 123 };
        immeubleServiceStub.find.resolves(foundImmeuble);
        immeubleServiceStub.retrieve.resolves([foundImmeuble]);

        // WHEN
        comp.beforeRouteEnter({ params: { immeubleId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.immeuble).toBe(foundImmeuble);
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
