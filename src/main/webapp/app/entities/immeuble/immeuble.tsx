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
        Immeubles
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/immeuble/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Immeuble
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {immeubleList && immeubleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Libelle</th>
                <th>Adresse</th>
                <th>Ville</th>
                <th>Numero</th>
                <th>Nb Etages</th>
                <th>Syndic</th>
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
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/immeuble/${immeuble.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/immeuble/${immeuble.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Immeubles found</div>
        )}
      </div>
    </div>
  );
};

export default Immeuble;
