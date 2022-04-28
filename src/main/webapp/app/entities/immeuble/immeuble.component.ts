import { mixins } from 'vue-class-component';
import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IImmeuble } from '@/shared/model/immeuble.model';

import JhiDataUtils from '@/shared/data/data-utils.service';

import ImmeubleService from './immeuble.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Immeuble extends mixins(JhiDataUtils) {
  @Inject('immeubleService') private immeubleService: () => ImmeubleService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public immeubles: IImmeuble[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllImmeubles();
  }

  public clear(): void {
    this.retrieveAllImmeubles();
  }

  public retrieveAllImmeubles(): void {
    this.isFetching = true;
    this.immeubleService()
      .retrieve()
      .then(
        res => {
          this.immeubles = res.data;
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

  public prepareRemove(instance: IImmeuble): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeImmeuble(): void {
    this.immeubleService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('syndicWebApp.immeuble.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllImmeubles();
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
