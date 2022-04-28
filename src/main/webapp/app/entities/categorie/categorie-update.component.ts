import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import DepenseService from '@/entities/depense/depense.service';
import { IDepense } from '@/shared/model/depense.model';

import RevenuService from '@/entities/revenu/revenu.service';
import { IRevenu } from '@/shared/model/revenu.model';

import { ICategorie, Categorie } from '@/shared/model/categorie.model';
import CategorieService from './categorie.service';

const validations: any = {
  categorie: {
    libelle: {},
  },
};

@Component({
  validations,
})
export default class CategorieUpdate extends Vue {
  @Inject('categorieService') private categorieService: () => CategorieService;
  @Inject('alertService') private alertService: () => AlertService;

  public categorie: ICategorie = new Categorie();

  @Inject('depenseService') private depenseService: () => DepenseService;

  public depenses: IDepense[] = [];

  @Inject('revenuService') private revenuService: () => RevenuService;

  public revenus: IRevenu[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categorieId) {
        vm.retrieveCategorie(to.params.categorieId);
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
    if (this.categorie.id) {
      this.categorieService()
        .update(this.categorie)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.categorie.updated', { param: param.id });
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
      this.categorieService()
        .create(this.categorie)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.categorie.created', { param: param.id });
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

  public retrieveCategorie(categorieId): void {
    this.categorieService()
      .find(categorieId)
      .then(res => {
        this.categorie = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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
