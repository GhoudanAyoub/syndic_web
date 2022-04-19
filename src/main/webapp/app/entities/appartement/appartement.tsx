import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppartement } from 'app/shared/model/appartement.model';
import { getEntities } from './appartement.reducer';

export const Appartement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const appartementList = useAppSelector(state => state.appartement.entities);
  const loading = useAppSelector(state => state.appartement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="appartement-heading" data-cy="AppartementHeading">
        <Translate contentKey="syndicWebApp.appartement.home.title">Appartements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.appartement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/appartement/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.appartement.home.createLabel">Create new Appartement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {appartementList && appartementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.etage">Etage</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.surface">Surface</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.resident">Resident</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.appartement.immeuble">Immeuble</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {appartementList.map((appartement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/appartement/${appartement.id}`} color="link" size="sm">
                      {appartement.id}
                    </Button>
                  </td>
                  <td>{appartement.numero}</td>
                  <td>{appartement.etage}</td>
                  <td>{appartement.surface}</td>
                  <td>{appartement.resident ? <Link to={`/resident/${appartement.resident.id}`}>{appartement.resident.id}</Link> : ''}</td>
                  <td>{appartement.immeuble ? <Link to={`/immeuble/${appartement.immeuble.id}`}>{appartement.immeuble.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/appartement/${appartement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/appartement/${appartement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/appartement/${appartement.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.appartement.home.notFound">No Appartements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Appartement;
