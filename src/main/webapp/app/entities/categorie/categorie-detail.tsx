import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './categorie.reducer';

export const CategorieDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const categorieEntity = useAppSelector(state => state.categorie.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categorieDetailsHeading">Categorie</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{categorieEntity.id}</dd>
          <dt>
            <span id="libelle">Libelle</span>
          </dt>
          <dd>{categorieEntity.libelle}</dd>
          <dt>Depense</dt>
          <dd>{categorieEntity.depense ? categorieEntity.depense.id : ''}</dd>
          <dt>Revenu</dt>
          <dd>{categorieEntity.revenu ? categorieEntity.revenu.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/categorie" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categorie/${categorieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategorieDetail;
