/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import SyndicComponent from '@/entities/syndic/syndic.vue';
import SyndicClass from '@/entities/syndic/syndic.component';
import SyndicService from '@/entities/syndic/syndic.service';
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
  describe('Syndic Management Component', () => {
    let wrapper: Wrapper<SyndicClass>;
    let comp: SyndicClass;
    let syndicServiceStub: SinonStubbedInstance<SyndicService>;

    beforeEach(() => {
      syndicServiceStub = sinon.createStubInstance<SyndicService>(SyndicService);
      syndicServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<SyndicClass>(SyndicComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          syndicService: () => syndicServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      syndicServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 123 }] });

      // WHEN
      comp.retrieveAllSyndics();
      await comp.$nextTick();

      // THEN
      expect(syndicServiceStub.retrieve.called).toBeTruthy();
      expect(comp.syndics[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      syndicServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 123 });
      expect(syndicServiceStub.retrieve.callCount).toEqual(1);

      comp.removeSyndic();
      await comp.$nextTick();

      // THEN
      expect(syndicServiceStub.delete.called).toBeTruthy();
      expect(syndicServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
