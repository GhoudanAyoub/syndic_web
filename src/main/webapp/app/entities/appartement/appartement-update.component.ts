import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PayementService from '@/entities/payement/payement.service';
import { IPayement } from '@/shared/model/payement.model';

import ResidentService from '@/entities/resident/resident.service';
import { IResident } from '@/shared/model/resident.model';

import ImmeubleService from '@/entities/immeuble/immeuble.service';
import { IImmeuble } from '@/shared/model/immeuble.model';

import { IAppartement, Appartement } from '@/shared/model/appartement.model';
import AppartementService from './appartement.service';

const validations: any = {
  appartement: {
    numero: {},
    etage: {},
    surface: {},
  },
};

@Component({
  validations,
})
export default class AppartementUpdate extends Vue {
  @Inject('appartementService') private appartementService: () => AppartementService;
  @Inject('alertService') private alertService: () => AlertService;

  public appartement: IAppartement = new Appartement();

  @Inject('payementService') private payementService: () => PayementService;

  public payements: IPayement[] = [];

  @Inject('residentService') private residentService: () => ResidentService;

  public residents: IResident[] = [];

  @Inject('immeubleService') private immeubleService: () => ImmeubleService;

  public immeubles: IImmeuble[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.appartementId) {
        vm.retrieveAppartement(to.params.appartementId);
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
    if (this.appartement.id) {
      this.appartementService()
        .update(this.appartement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.appartement.updated', { param: param.id });
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
      this.appartementService()
        .create(this.appartement)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.appartement.created', { param: param.id });
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

  public retrieveAppartement(appartementId): void {
    this.appartementService()
      .find(appartementId)
      .then(res => {
        this.appartement = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.payementService()
      .retrieve()
      .then(res => {
        this.payements = res.data;
      });
    this.residentService()
      .retrieve()
      .then(res => {
        this.residents = res.data;
      });
    this.immeubleService()
      .retrieve()
      .then(res => {
        this.immeubles = res.data;
      });
  }
}
