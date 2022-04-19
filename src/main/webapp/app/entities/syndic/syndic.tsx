import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISyndic } from 'app/shared/model/syndic.model';
import { getEntities } from './syndic.reducer';

export const Syndic = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const syndicList = useAppSelector(state => state.syndic.entities);
  const loading = useAppSelector(state => state.syndic.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="syndic-heading" data-cy="SyndicHeading">
        <Translate contentKey="syndicWebApp.syndic.home.title">Syndics</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.syndic.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/syndic/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.syndic.home.createLabel">Create new Syndic</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {syndicList && syndicList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.syndic.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.syndic.salaire">Salaire</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.syndic.personne">Personne</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {syndicList.map((syndic, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/syndic/${syndic.id}`} color="link" size="sm">
                      {syndic.id}
                    </Button>
                  </td>
                  <td>{syndic.salaire}</td>
                  <td>{syndic.personne ? <Link to={`/personne/${syndic.personne.id}`}>{syndic.personne.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/syndic/${syndic.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/syndic/${syndic.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/syndic/${syndic.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="syndicWebApp.syndic.home.notFound">No Syndics found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Syndic;
