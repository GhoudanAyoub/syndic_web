import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Immeuble from './immeuble';
import ImmeubleDetail from './immeuble-detail';
import ImmeubleUpdate from './immeuble-update';
import ImmeubleDeleteDialog from './immeuble-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ImmeubleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ImmeubleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ImmeubleDetail} />
      <ErrorBoundaryRoute path={match.url} component={Immeuble} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ImmeubleDeleteDialog} />
  </>
);

export default Routes;
