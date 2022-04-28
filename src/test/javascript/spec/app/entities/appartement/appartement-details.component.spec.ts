/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import AppartementDetailComponent from '@/entities/appartement/appartement-details.vue';
import AppartementClass from '@/entities/appartement/appartement-details.component';
import AppartementService from '@/entities/appartement/appartement.service';
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
  describe('Appartement Management Detail Component', () => {
    let wrapper: Wrapper<AppartementClass>;
    let comp: AppartementClass;
    let appartementServiceStub: SinonStubbedInstance<AppartementService>;

    beforeEach(() => {
      appartementServiceStub = sinon.createStubInstance<AppartementService>(AppartementService);

      wrapper = shallowMount<AppartementClass>(AppartementDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { appartementService: () => appartementServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundAppartement = { id: 123 };
        appartementServiceStub.find.resolves(foundAppartement);

        // WHEN
        comp.retrieveAppartement(123);
        await comp.$nextTick();

        // THEN
        expect(comp.appartement).toBe(foundAppartement);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundAppartement = { id: 123 };
        appartementServiceStub.find.resolves(foundAppartement);

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
