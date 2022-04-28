/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ResidentDetailComponent from '@/entities/resident/resident-details.vue';
import ResidentClass from '@/entities/resident/resident-details.component';
import ResidentService from '@/entities/resident/resident.service';
import router from '@/router';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(VueRouter);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('router-link', {});

describe('Component Tests', () => {
  describe('Resident Management Detail Component', () => {
    let wrapper: Wrapper<ResidentClass>;
    let comp: ResidentClass;
    let residentServiceStub: SinonStubbedInstance<ResidentService>;

    beforeEach(() => {
      residentServiceStub = sinon.createStubInstance<ResidentService>(ResidentService);

      wrapper = shallowMount<ResidentClass>(ResidentDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { residentService: () => residentServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundResident = { id: 123 };
        residentServiceStub.find.resolves(foundResident);

        // WHEN
        comp.retrieveResident(123);
        await comp.$nextTick();

        // THEN
        expect(comp.resident).toBe(foundResident);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundResident = { id: 123 };
        residentServiceStub.find.resolves(foundResident);

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
