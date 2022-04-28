import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { IImmeuble } from '@/shared/model/immeuble.model';
import ImmeubleService from './immeuble.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ImmeubleDetails extends mixins(JhiDataUtils) {
  @Inject('immeubleService') private immeubleService: () => ImmeubleService;
  @Inject('alertService') private alertService: () => AlertService;

  public immeuble: IImmeuble = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.immeubleId) {
        vm.retrieveImmeuble(to.params.immeubleId);
      }
    });
  }

  public retrieveImmeuble(immeubleId) {
    this.immeubleService()
      .find(immeubleId)
      .then(res => {
        this.immeuble = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
