import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './personne.reducer';

export const PersonneDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personneEntity = useAppSelector(state => state.personne.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personneDetailsHeading">Personne</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{personneEntity.id}</dd>
          <dt>
            <span id="nom">Nom</span>
          </dt>
          <dd>{personneEntity.nom}</dd>
          <dt>
            <span id="prenom">Prenom</span>
          </dt>
          <dd>{personneEntity.prenom}</dd>
          <dt>
            <span id="email">Email</span>
          </dt>
          <dd>{personneEntity.email}</dd>
          <dt>
            <span id="motPasse">Mot Passe</span>
          </dt>
          <dd>{personneEntity.motPasse}</dd>
          <dt>
            <span id="adresse">Adresse</span>
          </dt>
          <dd>{personneEntity.adresse}</dd>
        </dl>
        <Button tag={Link} to="/personne" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personne/${personneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonneDetail;
