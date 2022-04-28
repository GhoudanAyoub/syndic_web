import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPayement } from '@/shared/model/payement.model';
import PayementService from './payement.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PayementDetails extends Vue {
  @Inject('payementService') private payementService: () => PayementService;
  @Inject('alertService') private alertService: () => AlertService;

  public payement: IPayement = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.payementId) {
        vm.retrievePayement(to.params.payementId);
      }
    });
  }

  public retrievePayement(payementId) {
    this.payementService()
      .find(payementId)
      .then(res => {
        this.payement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
