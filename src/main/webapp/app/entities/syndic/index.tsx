import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Syndic from './syndic';
import SyndicDetail from './syndic-detail';
import SyndicUpdate from './syndic-update';
import SyndicDeleteDialog from './syndic-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={SyndicUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={SyndicUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={SyndicDetail} />
      <ErrorBoundaryRoute path={match.url} component={Syndic} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={SyndicDeleteDialog} />
  </>
);

export default Routes;
