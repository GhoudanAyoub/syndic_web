import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Appartement from './appartement';
import AppartementDetail from './appartement-detail';
import AppartementUpdate from './appartement-update';
import AppartementDeleteDialog from './appartement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AppartementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AppartementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AppartementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Appartement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AppartementDeleteDialog} />
  </>
);

export default Routes;
