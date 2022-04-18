import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Resident from './resident';
import ResidentDetail from './resident-detail';
import ResidentUpdate from './resident-update';
import ResidentDeleteDialog from './resident-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ResidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ResidentUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ResidentDetail} />
      <ErrorBoundaryRoute path={match.url} component={Resident} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ResidentDeleteDialog} />
  </>
);

export default Routes;
