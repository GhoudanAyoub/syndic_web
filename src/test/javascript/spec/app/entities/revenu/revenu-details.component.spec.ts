/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import RevenuDetailComponent from '@/entities/revenu/revenu-details.vue';
import RevenuClass from '@/entities/revenu/revenu-details.component';
import RevenuService from '@/entities/revenu/revenu.service';
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
  describe('Revenu Management Detail Component', () => {
    let wrapper: Wrapper<RevenuClass>;
    let comp: RevenuClass;
    let revenuServiceStub: SinonStubbedInstance<RevenuService>;

    beforeEach(() => {
      revenuServiceStub = sinon.createStubInstance<RevenuService>(RevenuService);

      wrapper = shallowMount<RevenuClass>(RevenuDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { revenuService: () => revenuServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundRevenu = { id: 123 };
        revenuServiceStub.find.resolves(foundRevenu);

        // WHEN
        comp.retrieveRevenu(123);
        await comp.$nextTick();

        // THEN
        expect(comp.revenu).toBe(foundRevenu);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundRevenu = { id: 123 };
        revenuServiceStub.find.resolves(foundRevenu);

        // WHEN
        comp.beforeRouteEnter({ params: { revenuId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.revenu).toBe(foundRevenu);
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
