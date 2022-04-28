import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CategorieService from '@/entities/categorie/categorie.service';
import { ICategorie } from '@/shared/model/categorie.model';

import ImmeubleService from '@/entities/immeuble/immeuble.service';
import { IImmeuble } from '@/shared/model/immeuble.model';

import { IRevenu, Revenu } from '@/shared/model/revenu.model';
import RevenuService from './revenu.service';

const validations: any = {
  revenu: {
    montant: {},
    date: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class RevenuUpdate extends Vue {
  @Inject('revenuService') private revenuService: () => RevenuService;
  @Inject('alertService') private alertService: () => AlertService;

  public revenu: IRevenu = new Revenu();

  @Inject('categorieService') private categorieService: () => CategorieService;

  public categories: ICategorie[] = [];

  @Inject('immeubleService') private immeubleService: () => ImmeubleService;

  public immeubles: IImmeuble[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.revenuId) {
        vm.retrieveRevenu(to.params.revenuId);
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
    if (this.revenu.id) {
      this.revenuService()
        .update(this.revenu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.revenu.updated', { param: param.id });
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
      this.revenuService()
        .create(this.revenu)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('syndicWebApp.revenu.created', { param: param.id });
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

  public retrieveRevenu(revenuId): void {
    this.revenuService()
      .find(revenuId)
      .then(res => {
        this.revenu = res;
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
