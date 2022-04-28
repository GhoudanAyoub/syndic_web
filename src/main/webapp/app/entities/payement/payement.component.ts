import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPayement } from '@/shared/model/payement.model';

import PayementService from './payement.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Payement extends Vue {
  @Inject('payementService') private payementService: () => PayementService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public payements: IPayement[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPayements();
  }

  public clear(): void {
    this.retrieveAllPayements();
  }

  public retrieveAllPayements(): void {
    this.isFetching = true;
    this.payementService()
      .retrieve()
      .then(
        res => {
          this.payements = res.data;
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

  public prepareRemove(instance: IPayement): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePayement(): void {
    this.payementService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.payement.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPayements();
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
