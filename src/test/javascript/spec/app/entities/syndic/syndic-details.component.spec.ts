/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import VueRouter from 'vue-router';

import * as config from '@/shared/config/config';
import SyndicDetailComponent from '@/entities/syndic/syndic-details.vue';
import SyndicClass from '@/entities/syndic/syndic-details.component';
import SyndicService from '@/entities/syndic/syndic.service';
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
  describe('Syndic Management Detail Component', () => {
    let wrapper: Wrapper<SyndicClass>;
    let comp: SyndicClass;
    let syndicServiceStub: SinonStubbedInstance<SyndicService>;

    beforeEach(() => {
      syndicServiceStub = sinon.createStubInstance<SyndicService>(SyndicService);

      wrapper = shallowMount<SyndicClass>(SyndicDetailComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: { syndicService: () => syndicServiceStub, alertService: () => new AlertService() },
      });
      comp = wrapper.vm;
    });

    describe('OnInit', () => {
      it('Should call load all on init', async () => {
        // GIVEN
        const foundSyndic = { id: 123 };
        syndicServiceStub.find.resolves(foundSyndic);

        // WHEN
        comp.retrieveSyndic(123);
        await comp.$nextTick();

        // THEN
        expect(comp.syndic).toBe(foundSyndic);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSyndic = { id: 123 };
        syndicServiceStub.find.resolves(foundSyndic);

        // WHEN
        comp.beforeRouteEnter({ params: { syndicId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.syndic).toBe(foundSyndic);
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
