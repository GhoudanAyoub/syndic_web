import { Component, Vue, Inject } from 'vue-property-decorator';

import { IDepense } from '@/shared/model/depense.model';
import DepenseService from './depense.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class DepenseDetails extends Vue {
  @Inject('depenseService') private depenseService: () => DepenseService;
  @Inject('alertService') private alertService: () => AlertService;

  public depense: IDepense = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.depenseId) {
        vm.retrieveDepense(to.params.depenseId);
      }
    });
  }

  public retrieveDepense(depenseId) {
    this.depenseService()
      .find(depenseId)
      .then(res => {
        this.depense = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
