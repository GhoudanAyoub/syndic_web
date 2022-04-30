import { Component, Inject } from 'vue-property-decorator';

import { mixins } from 'vue-class-component';
import JhiDataUtils from '@/shared/data/data-utils.service';

import AlertService from '@/shared/alert/alert.service';

import AppartementService from '@/entities/appartement/appartement.service';
import { IAppartement } from '@/shared/model/appartement.model';

import { IResident, Resident } from '@/shared/model/resident.model';
import ResidentService from './resident.service';

const validations: any = {
  resident: {
    email: {},
    motPasse: {},
    nom: {},
    prenom: {},
    adresse: {},
    tel: {},
    photo: {},
  },
};

@Component({
  validations,
})
export default class ResidentUpdate extends mixins(JhiDataUtils) {
  @Inject('residentService') private residentService: () => ResidentService;
  @Inject('alertService') private alertService: () => AlertService;

  public resident: IResident = new Resident();

  @Inject('appartementService') private appartementService: () => AppartementService;

  public appartements: IAppartement[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.residentId) {
        vm.retrieveResident(to.params.residentId);
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
    if (this.resident.id) {
      this.residentService()
        .update(this.resident)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.resident.updated', { param: param.id });
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
      this.residentService()
        .create(this.resident)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.resident.created', { param: param.id });
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

  public retrieveResident(residentId): void {
    this.residentService()
      .find(residentId)
      .then(res => {
        this.resident = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public clearInputImage(field, fieldContentType, idInput): void {
    if (this.resident && field && fieldContentType) {
      if (Object.prototype.hasOwnProperty.call(this.resident, field)) {
        this.resident[field] = null;
      }
      if (Object.prototype.hasOwnProperty.call(this.resident, fieldContentType)) {
        this.resident[fieldContentType] = null;
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
  }
}
