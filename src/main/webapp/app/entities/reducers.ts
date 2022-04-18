import personne from 'app/entities/personne/personne.reducer';
import resident from 'app/entities/resident/resident.reducer';
import syndic from 'app/entities/syndic/syndic.reducer';
import immeuble from 'app/entities/immeuble/immeuble.reducer';
import appartement from 'app/entities/appartement/appartement.reducer';
import revenu from 'app/entities/revenu/revenu.reducer';
import depense from 'app/entities/depense/depense.reducer';
import categorie from 'app/entities/categorie/categorie.reducer';
import payement from 'app/entities/payement/payement.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  personne,
  resident,
  syndic,
  immeuble,
  appartement,
  revenu,
  depense,
  categorie,
  payement,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
