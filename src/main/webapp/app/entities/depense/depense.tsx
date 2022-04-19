import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDepense } from 'app/shared/model/depense.model';
import { getEntities } from './depense.reducer';

export const Depense = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const depenseList = useAppSelector(state => state.depense.entities);
  const loading = useAppSelector(state => state.depense.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="depense-heading" data-cy="DepenseHeading">
        <Translate contentKey="syndicWebApp.depense.home.title">Depenses</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.depense.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/depense/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.depense.home.createLabel">Create new Depense</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {depenseList && depenseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.depense.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.depense.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.depense.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.depense.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.depense.immeuble">Immeuble</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {depenseList.map((depense, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/depense/${depense.id}`} color="link" size="sm">
                      {depense.id}
                    </Button>
                  </td>
                  <td>{depense.montant}</td>
                  <td>{depense.date ? <TextFormat type="date" value={depense.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{depense.description}</td>
                  <td>{depense.immeuble ? <Link to={`/immeuble/${depense.immeuble.id}`}>{depense.immeuble.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/depense/${depense.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/depense/${depense.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/depense/${depense.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.depense.home.notFound">No Depenses found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Depense;
