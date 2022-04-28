import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IRevenu } from '@/shared/model/revenu.model';

import RevenuService from './revenu.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Revenu extends Vue {
  @Inject('revenuService') private revenuService: () => RevenuService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public revenus: IRevenu[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllRevenus();
  }

  public clear(): void {
    this.retrieveAllRevenus();
  }

  public retrieveAllRevenus(): void {
    this.isFetching = true;
    this.revenuService()
      .retrieve()
      .then(
        res => {
          this.revenus = res.data;
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

  public prepareRemove(instance: IRevenu): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeRevenu(): void {
    this.revenuService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.revenu.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllRevenus();
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
