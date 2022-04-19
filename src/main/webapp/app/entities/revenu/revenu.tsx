import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IRevenu } from 'app/shared/model/revenu.model';
import { getEntities } from './revenu.reducer';

export const Revenu = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const revenuList = useAppSelector(state => state.revenu.entities);
  const loading = useAppSelector(state => state.revenu.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="revenu-heading" data-cy="RevenuHeading">
        <Translate contentKey="syndicWebApp.revenu.home.title">Revenus</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.revenu.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/revenu/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.revenu.home.createLabel">Create new Revenu</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {revenuList && revenuList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.revenu.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.revenu.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.revenu.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.revenu.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.revenu.immeuble">Immeuble</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {revenuList.map((revenu, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/revenu/${revenu.id}`} color="link" size="sm">
                      {revenu.id}
                    </Button>
                  </td>
                  <td>{revenu.montant}</td>
                  <td>{revenu.date ? <TextFormat type="date" value={revenu.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{revenu.description}</td>
                  <td>{revenu.immeuble ? <Link to={`/immeuble/${revenu.immeuble.id}`}>{revenu.immeuble.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/revenu/${revenu.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/revenu/${revenu.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/revenu/${revenu.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.revenu.home.notFound">No Revenus found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Revenu;
