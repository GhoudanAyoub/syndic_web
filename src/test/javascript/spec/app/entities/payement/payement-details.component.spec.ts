/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import PayementDetailComponent from '@/entities/payement/payement-details.vue';
import PayementClass from '@/entities/payement/payement-details.component';
import PayementService from '@/entities/payement/payement.service';
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
  describe('Payement Management Detail Component', () => {
    let wrapper: Wrapper<PayementClass>;
    let comp: PayementClass;
    let payementServiceStub: SinonStubbedInstance<PayementService>;

    beforeEach(() => {
      payementServiceStub = sinon.createStubInstance<PayementService>(PayementService);

      wrapper = shallowMount<PayementClass>(PayementDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { payementService: () => payementServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundPayement = { id: 123 };
        payementServiceStub.find.resolves(foundPayement);

        // WHEN
        comp.retrievePayement(123);
        await comp.$nextTick();

        // THEN
        expect(comp.payement).toBe(foundPayement);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundPayement = { id: 123 };
        payementServiceStub.find.resolves(foundPayement);

        // WHEN
        comp.beforeRouteEnter({ params: { payementId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.payement).toBe(foundPayement);
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
