import { Component, Vue, Inject } from 'vue-property-decorator';

import { IResident } from '@/shared/model/resident.model';
import ResidentService from './resident.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ResidentDetails extends Vue {
  @Inject('residentService') private residentService: () => ResidentService;
  @Inject('alertService') private alertService: () => AlertService;

  public resident: IResident = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.residentId) {
        vm.retrieveResident(to.params.residentId);
      }
    });
  }

  public retrieveResident(residentId) {
    this.residentService()
      .find(residentId)
      .then(res => {
        this.resident = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
