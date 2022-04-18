import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Revenu from './revenu';
import RevenuDetail from './revenu-detail';
import RevenuUpdate from './revenu-update';
import RevenuDeleteDialog from './revenu-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={RevenuUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={RevenuUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={RevenuDetail} />
      <ErrorBoundaryRoute path={match.url} component={Revenu} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={RevenuDeleteDialog} />
  </>
);

export default Routes;
