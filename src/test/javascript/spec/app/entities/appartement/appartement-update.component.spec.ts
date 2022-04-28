/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AppartementUpdateComponent from '@/entities/appartement/appartement-update.vue';
import AppartementClass from '@/entities/appartement/appartement-update.component';
import AppartementService from '@/entities/appartement/appartement.service';

import PayementService from '@/entities/payement/payement.service';

import ResidentService from '@/entities/resident/resident.service';

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
  describe('Appartement Management Update Component', () => {
    let wrapper: Wrapper<AppartementClass>;
    let comp: AppartementClass;
    let appartementServiceStub: SinonStubbedInstance<AppartementService>;

    beforeEach(() => {
      appartementServiceStub = sinon.createStubInstance<AppartementService>(AppartementService);

      wrapper = shallowMount<AppartementClass>(AppartementUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          appartementService: () => appartementServiceStub,
          alertService: () => new AlertService(),

          payementService: () =>
            sinon.createStubInstance<PayementService>(PayementService, {
              retrieve: sinon.stub().resolves({}),
            } as any),

          residentService: () =>
            sinon.createStubInstance<ResidentService>(ResidentService, {
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
        comp.appartement = entity;
        appartementServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appartementServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.appartement = entity;
        appartementServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(appartementServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAppartement = { id: 123 };
        appartementServiceStub.find.resolves(foundAppartement);
        appartementServiceStub.retrieve.resolves([foundAppartement]);

        // WHEN
        comp.beforeRouteEnter({ params: { appartementId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.appartement).toBe(foundAppartement);
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
