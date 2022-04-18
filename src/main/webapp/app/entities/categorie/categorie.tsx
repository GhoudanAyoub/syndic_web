import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntities } from './categorie.reducer';

export const Categorie = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const categorieList = useAppSelector(state => state.categorie.entities);
  const loading = useAppSelector(state => state.categorie.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="categorie-heading" data-cy="CategorieHeading">
        Categories
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/categorie/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Categorie
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {categorieList && categorieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Libelle</th>
                <th>Depense</th>
                <th>Revenu</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {categorieList.map((categorie, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/categorie/${categorie.id}`} color="link" size="sm">
                      {categorie.id}
                    </Button>
                  </td>
                  <td>{categorie.libelle}</td>
                  <td>{categorie.depense ? <Link to={`/depense/${categorie.depense.id}`}>{categorie.depense.id}</Link> : ''}</td>
                  <td>{categorie.revenu ? <Link to={`/revenu/${categorie.revenu.id}`}>{categorie.revenu.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/categorie/${categorie.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/categorie/${categorie.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/categorie/${categorie.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Categories found</div>
        )}
      </div>
    </div>
  );
};

export default Categorie;
