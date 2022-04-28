/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ResidentUpdateComponent from '@/entities/resident/resident-update.vue';
import ResidentClass from '@/entities/resident/resident-update.component';
import ResidentService from '@/entities/resident/resident.service';

import AppartementService from '@/entities/appartement/appartement.service';
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
  describe('Resident Management Update Component', () => {
    let wrapper: Wrapper<ResidentClass>;
    let comp: ResidentClass;
    let residentServiceStub: SinonStubbedInstance<ResidentService>;

    beforeEach(() => {
      residentServiceStub = sinon.createStubInstance<ResidentService>(ResidentService);

      wrapper = shallowMount<ResidentClass>(ResidentUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          residentService: () => residentServiceStub,
          alertService: () => new AlertService(),

          appartementService: () =>
            sinon.createStubInstance<AppartementService>(AppartementService, {
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
        comp.resident = entity;
        residentServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(residentServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.resident = entity;
        residentServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(residentServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResident = { id: 123 };
        residentServiceStub.find.resolves(foundResident);
        residentServiceStub.retrieve.resolves([foundResident]);

        // WHEN
        comp.beforeRouteEnter({ params: { residentId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.resident).toBe(foundResident);
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
