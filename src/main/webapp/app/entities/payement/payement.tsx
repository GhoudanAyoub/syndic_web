import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPayement } from 'app/shared/model/payement.model';
import { getEntities } from './payement.reducer';

export const Payement = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const payementList = useAppSelector(state => state.payement.entities);
  const loading = useAppSelector(state => state.payement.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="payement-heading" data-cy="PayementHeading">
        <Translate contentKey="syndicWebApp.payement.home.title">Payements</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.payement.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/payement/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.payement.home.createLabel">Create new Payement</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {payementList && payementList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.payement.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.payement.montant">Montant</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.payement.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.payement.description">Description</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.payement.appartement">Appartement</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {payementList.map((payement, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/payement/${payement.id}`} color="link" size="sm">
                      {payement.id}
                    </Button>
                  </td>
                  <td>{payement.montant}</td>
                  <td>{payement.date ? <TextFormat type="date" value={payement.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{payement.description}</td>
                  <td>
                    {payement.appartement ? <Link to={`/appartement/${payement.appartement.id}`}>{payement.appartement.id}</Link> : ''}
                  </td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/payement/${payement.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/payement/${payement.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/payement/${payement.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.payement.home.notFound">No Payements found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Payement;
