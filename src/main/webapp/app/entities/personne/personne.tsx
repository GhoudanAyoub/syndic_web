import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPersonne } from 'app/shared/model/personne.model';
import { getEntities } from './personne.reducer';

export const Personne = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const personneList = useAppSelector(state => state.personne.entities);
  const loading = useAppSelector(state => state.personne.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="personne-heading" data-cy="PersonneHeading">
        <Translate contentKey="syndicWebApp.personne.home.title">Personnes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="syndicWebApp.personne.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/personne/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="syndicWebApp.personne.home.createLabel">Create new Personne</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {personneList && personneList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="syndicWebApp.personne.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.nom">Nom</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.prenom">Prenom</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.email">Email</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.motPasse">Mot Passe</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.adresse">Adresse</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.ville">Ville</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.photo">Photo</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.tel">Tel</Translate>
                </th>
                <th>
                  <Translate contentKey="syndicWebApp.personne.internalUser">Internal User</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {personneList.map((personne, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/personne/${personne.id}`} color="link" size="sm">
                      {personne.id}
                    </Button>
                  </td>
                  <td>{personne.nom}</td>
                  <td>{personne.prenom}</td>
                  <td>{personne.email}</td>
                  <td>{personne.motPasse}</td>
                  <td>{personne.adresse}</td>
                  <td>{personne.ville}</td>
                  <td>{personne.photo}</td>
                  <td>{personne.tel}</td>
                  <td>{personne.internalUser ? personne.internalUser.id : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/personne/${personne.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/personne/${personne.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/personne/${personne.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="syndicWebApp.personne.home.notFound">No Personnes found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Personne;
