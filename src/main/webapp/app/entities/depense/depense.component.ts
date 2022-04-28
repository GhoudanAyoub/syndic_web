import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IDepense } from '@/shared/model/depense.model';

import DepenseService from './depense.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Depense extends Vue {
  @Inject('depenseService') private depenseService: () => DepenseService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public depenses: IDepense[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllDepenses();
  }

  public clear(): void {
    this.retrieveAllDepenses();
  }

  public retrieveAllDepenses(): void {
    this.isFetching = true;
    this.depenseService()
      .retrieve()
      .then(
        res => {
          this.depenses = res.data;
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

  public prepareRemove(instance: IDepense): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeDepense(): void {
    this.depenseService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.depense.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllDepenses();
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
