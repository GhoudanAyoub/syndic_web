import { Component, Vue, Inject } from 'vue-property-decorator';

import { IRevenu } from '@/shared/model/revenu.model';
import RevenuService from './revenu.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class RevenuDetails extends Vue {
  @Inject('revenuService') private revenuService: () => RevenuService;
  @Inject('alertService') private alertService: () => AlertService;

  public revenu: IRevenu = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.revenuId) {
        vm.retrieveRevenu(to.params.revenuId);
      }
    });
  }

  public retrieveRevenu(revenuId) {
    this.revenuService()
      .find(revenuId)
      .then(res => {
        this.revenu = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
