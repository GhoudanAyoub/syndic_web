/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SyndicUpdateComponent from '@/entities/syndic/syndic-update.vue';
import SyndicClass from '@/entities/syndic/syndic-update.component';
import SyndicService from '@/entities/syndic/syndic.service';

import UserService from '@/entities/user/user.service';

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
  describe('Syndic Management Update Component', () => {
    let wrapper: Wrapper<SyndicClass>;
    let comp: SyndicClass;
    let syndicServiceStub: SinonStubbedInstance<SyndicService>;

    beforeEach(() => {
      syndicServiceStub = sinon.createStubInstance<SyndicService>(SyndicService);

      wrapper = shallowMount<SyndicClass>(SyndicUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          syndicService: () => syndicServiceStub,
          alertService: () => new AlertService(),

          userService: () => new UserService(),

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
        comp.syndic = entity;
        syndicServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(syndicServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.syndic = entity;
        syndicServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(syndicServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundSyndic = { id: 123 };
        syndicServiceStub.find.resolves(foundSyndic);
        syndicServiceStub.retrieve.resolves([foundSyndic]);

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
