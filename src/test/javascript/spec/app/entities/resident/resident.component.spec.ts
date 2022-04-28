/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import ResidentComponent from '@/entities/resident/resident.vue';
import ResidentClass from '@/entities/resident/resident.component';
import ResidentService from '@/entities/resident/resident.service';
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
  describe('Resident Management Component', () => {
    let wrapper: Wrapper<ResidentClass>;
    let comp: ResidentClass;
    let residentServiceStub: SinonStubbedInstance<ResidentService>;

    beforeEach(() => {
      residentServiceStub = sinon.createStubInstance<ResidentService>(ResidentService);
      residentServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<ResidentClass>(ResidentComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          residentService: () => residentServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      residentServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllResidents();
      await comp.$nextTick();

      // THEN
      expect(residentServiceStub.retrieve.called).toBeTruthy();
      expect(comp.residents[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      residentServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(residentServiceStub.retrieve.callCount).toEqual(1);

      comp.removeResident();
      await comp.$nextTick();

      // THEN
      expect(residentServiceStub.delete.called).toBeTruthy();
      expect(residentServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
