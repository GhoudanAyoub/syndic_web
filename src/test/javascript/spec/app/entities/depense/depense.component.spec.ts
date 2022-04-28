/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import DepenseComponent from '@/entities/depense/depense.vue';
import DepenseClass from '@/entities/depense/depense.component';
import DepenseService from '@/entities/depense/depense.service';
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
  describe('Depense Management Component', () => {
    let wrapper: Wrapper<DepenseClass>;
    let comp: DepenseClass;
    let depenseServiceStub: SinonStubbedInstance<DepenseService>;

    beforeEach(() => {
      depenseServiceStub = sinon.createStubInstance<DepenseService>(DepenseService);
      depenseServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<DepenseClass>(DepenseComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          depenseService: () => depenseServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      depenseServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllDepenses();
      await comp.$nextTick();

      // THEN
      expect(depenseServiceStub.retrieve.called).toBeTruthy();
      expect(comp.depenses[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      depenseServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(depenseServiceStub.retrieve.callCount).toEqual(1);

      comp.removeDepense();
      await comp.$nextTick();

      // THEN
      expect(depenseServiceStub.delete.called).toBeTruthy();
      expect(depenseServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
