import { Component, Provide, Vue } from 'vue-property-decorator';

import UserService from '@/entities/user/user.service';
import ResidentService from './resident/resident.service';
import SyndicService from './syndic/syndic.service';
import ImmeubleService from './immeuble/immeuble.service';
import AppartementService from './appartement/appartement.service';
import RevenuService from './revenu/revenu.service';
import DepenseService from './depense/depense.service';
import CategorieService from './categorie/categorie.service';
import PayementService from './payement/payement.service';
// jhipster-needle-add-entity-service-to-entities-component-import - JHipster will import entities services here

@Component
export default class Entities extends Vue {
  @Provide('userService') private userService = () => new UserService();
  @Provide('residentService') private residentService = () => new ResidentService();
  @Provide('syndicService') private syndicService = () => new SyndicService();
  @Provide('immeubleService') private immeubleService = () => new ImmeubleService();
  @Provide('appartementService') private appartementService = () => new AppartementService();
  @Provide('revenuService') private revenuService = () => new RevenuService();
  @Provide('depenseService') private depenseService = () => new DepenseService();
  @Provide('categorieService') private categorieService = () => new CategorieService();
  @Provide('payementService') private payementService = () => new PayementService();
  // jhipster-needle-add-entity-service-to-entities-component - JHipster will import entities services here
}
