import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IResident } from '@/shared/model/resident.model';

import ResidentService from './resident.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Resident extends Vue {
  @Inject('residentService') private residentService: () => ResidentService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public residents: IResident[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllResidents();
  }

  public clear(): void {
    this.retrieveAllResidents();
  }

  public retrieveAllResidents(): void {
    this.isFetching = true;
    this.residentService()
      .retrieve()
      .then(
        res => {
          this.residents = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IResident): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeResident(): void {
    this.residentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.resident.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllResidents();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
