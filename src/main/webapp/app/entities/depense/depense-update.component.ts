import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CategorieService from '@/entities/categorie/categorie.service';
import { ICategorie } from '@/shared/model/categorie.model';

import ImmeubleService from '@/entities/immeuble/immeuble.service';
import { IImmeuble } from '@/shared/model/immeuble.model';

import { IDepense, Depense } from '@/shared/model/depense.model';
import DepenseService from './depense.service';

const validations: any = {
  depense: {
    montant: {},
    date: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class DepenseUpdate extends Vue {
  @Inject('depenseService') private depenseService: () => DepenseService;
  @Inject('alertService') private alertService: () => AlertService;

  public depense: IDepense = new Depense();

  @Inject('categorieService') private categorieService: () => CategorieService;

  public categories: ICategorie[] = [];

  @Inject('immeubleService') private immeubleService: () => ImmeubleService;

  public immeubles: IImmeuble[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.depenseId) {
        vm.retrieveDepense(to.params.depenseId);
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
    if (this.depense.id) {
      this.depenseService()
        .update(this.depense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.depense.updated', { param: param.id });
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
      this.depenseService()
        .create(this.depense)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.depense.created', { param: param.id });
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

  public retrieveDepense(depenseId): void {
    this.depenseService()
      .find(depenseId)
      .then(res => {
        this.depense = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.categorieService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
    this.immeubleService()
      .retrieve()
      .then(res => {
        this.immeubles = res.data;
      });
  }
}
