/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import ImmeubleDetailComponent from '@/entities/immeuble/immeuble-details.vue';
import ImmeubleClass from '@/entities/immeuble/immeuble-details.component';
import ImmeubleService from '@/entities/immeuble/immeuble.service';
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
  describe('Immeuble Management Detail Component', () => {
    let wrapper: Wrapper<ImmeubleClass>;
    let comp: ImmeubleClass;
    let immeubleServiceStub: SinonStubbedInstance<ImmeubleService>;

    beforeEach(() => {
      immeubleServiceStub = sinon.createStubInstance<ImmeubleService>(ImmeubleService);

      wrapper = shallowMount<ImmeubleClass>(ImmeubleDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { immeubleService: () => immeubleServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundImmeuble = { id: 123 };
        immeubleServiceStub.find.resolves(foundImmeuble);

        // WHEN
        comp.retrieveImmeuble(123);
        await comp.$nextTick();

        // THEN
        expect(comp.immeuble).toBe(foundImmeuble);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundImmeuble = { id: 123 };
        immeubleServiceStub.find.resolves(foundImmeuble);

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
