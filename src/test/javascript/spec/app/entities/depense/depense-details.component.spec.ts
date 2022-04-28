/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import DepenseDetailComponent from '@/entities/depense/depense-details.vue';
import DepenseClass from '@/entities/depense/depense-details.component';
import DepenseService from '@/entities/depense/depense.service';
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
  describe('Depense Management Detail Component', () => {
    let wrapper: Wrapper<DepenseClass>;
    let comp: DepenseClass;
    let depenseServiceStub: SinonStubbedInstance<DepenseService>;

    beforeEach(() => {
      depenseServiceStub = sinon.createStubInstance<DepenseService>(DepenseService);

      wrapper = shallowMount<DepenseClass>(DepenseDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { depenseService: () => depenseServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundDepense = { id: 123 };
        depenseServiceStub.find.resolves(foundDepense);

        // WHEN
        comp.retrieveDepense(123);
        await comp.$nextTick();

        // THEN
        expect(comp.depense).toBe(foundDepense);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundDepense = { id: 123 };
        depenseServiceStub.find.resolves(foundDepense);

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
