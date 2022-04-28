import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAppartement } from '@/shared/model/appartement.model';

import AppartementService from './appartement.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Appartement extends Vue {
  @Inject('appartementService') private appartementService: () => AppartementService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public appartements: IAppartement[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAppartements();
  }

  public clear(): void {
    this.retrieveAllAppartements();
  }

  public retrieveAllAppartements(): void {
    this.isFetching = true;
    this.appartementService()
      .retrieve()
      .then(
        res => {
          this.appartements = res.data;
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

  public prepareRemove(instance: IAppartement): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAppartement(): void {
    this.appartementService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.appartement.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAppartements();
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
