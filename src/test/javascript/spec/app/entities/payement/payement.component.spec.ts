/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import PayementComponent from '@/entities/payement/payement.vue';
import PayementClass from '@/entities/payement/payement.component';
import PayementService from '@/entities/payement/payement.service';
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
  describe('Payement Management Component', () => {
    let wrapper: Wrapper<PayementClass>;
    let comp: PayementClass;
    let payementServiceStub: SinonStubbedInstance<PayementService>;

    beforeEach(() => {
      payementServiceStub = sinon.createStubInstance<PayementService>(PayementService);
      payementServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<PayementClass>(PayementComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          payementService: () => payementServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      payementServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllPayements();
      await comp.$nextTick();

      // THEN
      expect(payementServiceStub.retrieve.called).toBeTruthy();
      expect(comp.payements[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      payementServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(payementServiceStub.retrieve.callCount).toEqual(1);

      comp.removePayement();
      await comp.$nextTick();

      // THEN
      expect(payementServiceStub.delete.called).toBeTruthy();
      expect(payementServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
