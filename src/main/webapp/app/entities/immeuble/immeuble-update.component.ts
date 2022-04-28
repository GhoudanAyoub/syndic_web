import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AlertService from '@/shared/alert/alert.service';

import AppartementService from '@/entities/appartement/appartement.service';
import { IAppartement } from '@/shared/model/appartement.model';

import SyndicService from '@/entities/syndic/syndic.service';
import { ISyndic } from '@/shared/model/syndic.model';

import DepenseService from '@/entities/depense/depense.service';
import { IDepense } from '@/shared/model/depense.model';

import RevenuService from '@/entities/revenu/revenu.service';
import { IRevenu } from '@/shared/model/revenu.model';

import { IImmeuble, Immeuble } from '@/shared/model/immeuble.model';
import ImmeubleService from './immeuble.service';

const validations: any = {
  immeuble: {
    libelle: {},
    adresse: {},
    ville: {},
    numero: {},
    nbEtages: {},
    photo: {},
  },
};

@Component({
  validations,
})
export default class ImmeubleUpdate extends mixins(JhiDataUtils) {
  @Inject('immeubleService') private immeubleService: () => ImmeubleService;
  @Inject('alertService') private alertService: () => AlertService;

  public immeuble: IImmeuble = new Immeuble();

  @Inject('appartementService') private appartementService: () => AppartementService;

  public appartements: IAppartement[] = [];

  @Inject('syndicService') private syndicService: () => SyndicService;

  public syndics: ISyndic[] = [];

  @Inject('depenseService') private depenseService: () => DepenseService;

  public depenses: IDepense[] = [];

  @Inject('revenuService') private revenuService: () => RevenuService;

  public revenus: IRevenu[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.immeubleId) {
        vm.retrieveImmeuble(to.params.immeubleId);
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
    if (this.immeuble.id) {
      this.immeubleService()
        .update(this.immeuble)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.immeuble.updated', { param: param.id });
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
      this.immeubleService()
        .create(this.immeuble)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.immeuble.created', { param: param.id });
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

  public retrieveImmeuble(immeubleId): void {
    this.immeubleService()
      .find(immeubleId)
      .then(res => {
        this.immeuble = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.immeuble && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.immeuble, field)) {
        this.immeuble[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.immeuble, fieldContentType)) {
        this.immeuble[fieldContentType] = null;
      }
      if (idInput) {
        (<any>this).$refs[idInput] = null;
      }
    }
  }

  public initRelationships(): void {
    this.appartementService()
      .retrieve()
      .then(res => {
        this.appartements = res.data;
      });
    this.syndicService()
      .retrieve()
      .then(res => {
        this.syndics = res.data;
      });
    this.depenseService()
      .retrieve()
      .then(res => {
        this.depenses = res.data;
      });
    this.revenuService()
      .retrieve()
      .then(res => {
        this.revenus = res.data;
      });
  }
}
