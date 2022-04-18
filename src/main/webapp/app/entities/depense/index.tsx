import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Depense from './depense';
import DepenseDetail from './depense-detail';
import DepenseUpdate from './depense-update';
import DepenseDeleteDialog from './depense-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={DepenseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={DepenseUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={DepenseDetail} />
      <ErrorBoundaryRoute path={match.url} component={Depense} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={DepenseDeleteDialog} />
  </>
);

export default Routes;
