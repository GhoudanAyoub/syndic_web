import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAppartement } from '@/shared/model/appartement.model';
import AppartementService from './appartement.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AppartementDetails extends Vue {
  @Inject('appartementService') private appartementService: () => AppartementService;
  @Inject('alertService') private alertService: () => AlertService;

  public appartement: IAppartement = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.appartementId) {
        vm.retrieveAppartement(to.params.appartementId);
      }
    });
  }

  public retrieveAppartement(appartementId) {
    this.appartementService()
      .find(appartementId)
      .then(res => {
        this.appartement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
