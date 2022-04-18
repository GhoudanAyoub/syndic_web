import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Personne from './personne';
import Resident from './resident';
import Syndic from './syndic';
import Immeuble from './immeuble';
import Appartement from './appartement';
import Revenu from './revenu';
import Depense from './depense';
import Categorie from './categorie';
import Payement from './payement';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}personne`} component={Personne} />
        <ErrorBoundaryRoute path={`${match.url}resident`} component={Resident} />
        <ErrorBoundaryRoute path={`${match.url}syndic`} component={Syndic} />
        <ErrorBoundaryRoute path={`${match.url}immeuble`} component={Immeuble} />
        <ErrorBoundaryRoute path={`${match.url}appartement`} component={Appartement} />
        <ErrorBoundaryRoute path={`${match.url}revenu`} component={Revenu} />
        <ErrorBoundaryRoute path={`${match.url}depense`} component={Depense} />
        <ErrorBoundaryRoute path={`${match.url}categorie`} component={Categorie} />
        <ErrorBoundaryRoute path={`${match.url}payement`} component={Payement} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
