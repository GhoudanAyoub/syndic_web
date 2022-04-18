import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Payement from './payement';
import PayementDetail from './payement-detail';
import PayementUpdate from './payement-update';
import PayementDeleteDialog from './payement-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PayementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PayementUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PayementDetail} />
      <ErrorBoundaryRoute path={match.url} component={Payement} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PayementDeleteDialog} />
  </>
);

export default Routes;
