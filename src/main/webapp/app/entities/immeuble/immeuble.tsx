import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IImmeuble } from 'app/shared/model/immeuble.model';
import { getEntities } from './immeuble.reducer';

export const Immeuble = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const immeubleList = useAppSelector(state => state.immeuble.entities);
  const loading = useAppSelector(state => state.immeuble.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="immeuble-heading" data-cy="ImmeubleHeading">
        <Translate contentKey="syndicWebApp.immeuble.home.title">Immeubles</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.immeuble.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/immeuble/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.immeuble.home.createLabel">Create new Immeuble</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {immeubleList && immeubleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.libelle">Libelle</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.ville">Ville</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.numero">Numero</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.nbEtages">Nb Etages</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.immeuble.syndic">Syndic</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {immeubleList.map((immeuble, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/immeuble/${immeuble.id}`} color="link" size="sm">
                      {immeuble.id}
                    </Button>
                  </td>
                  <td>{immeuble.libelle}</td>
                  <td>{immeuble.adresse}</td>
                  <td>{immeuble.ville}</td>
                  <td>{immeuble.numero}</td>
                  <td>{immeuble.nbEtages}</td>
                  <td>{immeuble.syndic ? <Link to={`/syndic/${immeuble.syndic.id}`}>{immeuble.syndic.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/immeuble/${immeuble.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/immeuble/${immeuble.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/immeuble/${immeuble.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.immeuble.home.notFound">No Immeubles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Immeuble;
