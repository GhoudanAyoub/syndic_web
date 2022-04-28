/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RevenuComponent from '@/entities/revenu/revenu.vue';
import RevenuClass from '@/entities/revenu/revenu.component';
import RevenuService from '@/entities/revenu/revenu.service';
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
  describe('Revenu Management Component', () => {
    let wrapper: Wrapper<RevenuClass>;
    let comp: RevenuClass;
    let revenuServiceStub: SinonStubbedInstance<RevenuService>;

    beforeEach(() => {
      revenuServiceStub = sinon.createStubInstance<RevenuService>(RevenuService);
      revenuServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RevenuClass>(RevenuComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          revenuService: () => revenuServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      revenuServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllRevenus();
      await comp.$nextTick();

      // THEN
      expect(revenuServiceStub.retrieve.called).toBeTruthy();
      expect(comp.revenus[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      revenuServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(revenuServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRevenu();
      await comp.$nextTick();

      // THEN
      expect(revenuServiceStub.delete.called).toBeTruthy();
      expect(revenuServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
