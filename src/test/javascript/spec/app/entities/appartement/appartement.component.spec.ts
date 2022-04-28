/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import AppartementComponent from '@/entities/appartement/appartement.vue';
import AppartementClass from '@/entities/appartement/appartement.component';
import AppartementService from '@/entities/appartement/appartement.service';
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
  describe('Appartement Management Component', () => {
    let wrapper: Wrapper<AppartementClass>;
    let comp: AppartementClass;
    let appartementServiceStub: SinonStubbedInstance<AppartementService>;

    beforeEach(() => {
      appartementServiceStub = sinon.createStubInstance<AppartementService>(AppartementService);
      appartementServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<AppartementClass>(AppartementComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          appartementService: () => appartementServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      appartementServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllAppartements();
      await comp.$nextTick();

      // THEN
      expect(appartementServiceStub.retrieve.called).toBeTruthy();
      expect(comp.appartements[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      appartementServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(appartementServiceStub.retrieve.callCount).toEqual(1);

      comp.removeAppartement();
      await comp.$nextTick();

      // THEN
      expect(appartementServiceStub.delete.called).toBeTruthy();
      expect(appartementServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
