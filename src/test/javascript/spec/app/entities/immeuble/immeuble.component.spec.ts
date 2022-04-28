/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ImmeubleComponent from '@/entities/immeuble/immeuble.vue';
import ImmeubleClass from '@/entities/immeuble/immeuble.component';
import ImmeubleService from '@/entities/immeuble/immeuble.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('Immeuble Management Component', () => {
    let wrapper: Wrapper<ImmeubleClass>;
    let comp: ImmeubleClass;
    let immeubleServiceStub: SinonStubbedInstance<ImmeubleService>;

    beforeEach(() => {
      immeubleServiceStub = sinon.createStubInstance<ImmeubleService>(ImmeubleService);
      immeubleServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ImmeubleClass>(ImmeubleComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          immeubleService: () => immeubleServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      immeubleServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllImmeubles();
      await comp.$nextTick();

      // THEN
      expect(immeubleServiceStub.retrieve.called).toBeTruthy();
      expect(comp.immeubles[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      immeubleServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(immeubleServiceStub.retrieve.callCount).toEqual(1);

      comp.removeImmeuble();
      await comp.$nextTick();

      // THEN
      expect(immeubleServiceStub.delete.called).toBeTruthy();
      expect(immeubleServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
