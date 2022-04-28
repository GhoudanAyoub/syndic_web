import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import ImmeubleService from '@/entities/immeuble/immeuble.service';
import { IImmeuble } from '@/shared/model/immeuble.model';

import { ISyndic, Syndic } from '@/shared/model/syndic.model';
import SyndicService from './syndic.service';

const validations: any = {
  syndic: {
    adresse: {},
    tel: {},
    dateTravail: {},
    photo: {},
  },
};

@Component({
  validations,
})
export default class SyndicUpdate extends mixins(JhiDataUtils) {
  @Inject('syndicService') private syndicService: () => SyndicService;
  @Inject('alertService') private alertService: () => AlertService;

  public syndic: ISyndic = new Syndic();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('immeubleService') private immeubleService: () => ImmeubleService;

  public immeubles: IImmeuble[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.syndicId) {
        vm.retrieveSyndic(to.params.syndicId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.syndic.id) {
      this.syndicService()
        .update(this.syndic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.syndic.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.syndicService()
        .create(this.syndic)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.syndic.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveSyndic(syndicId): void {
    this.syndicService()
      .find(syndicId)
      .then(res => {
        this.syndic = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.syndic && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.syndic, field)) {
        this.syndic[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.syndic, fieldContentType)) {
        this.syndic[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.immeubleService()
      .retrieve()
      .then(res => {
        this.immeubles = res.data;
      });
  }
}
