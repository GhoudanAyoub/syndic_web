import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import { ISyndic } from '@/shared/model/syndic.model';
import SyndicService from './syndic.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class SyndicDetails extends mixins(JhiDataUtils) {
  @Inject('syndicService') private syndicService: () => SyndicService;
  @Inject('alertService') private alertService: () => AlertService;

  public syndic: ISyndic = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.syndicId) {
        vm.retrieveSyndic(to.params.syndicId);
      }
    });
  }

  public retrieveSyndic(syndicId) {
    this.syndicService()
      .find(syndicId)
      .then(res => {
        this.syndic = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
