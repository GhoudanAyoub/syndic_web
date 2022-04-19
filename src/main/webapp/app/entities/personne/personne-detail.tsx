import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
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
        <h2 data-cy="personneDetailsHeading">
          <Translate contentKey="syndicWebApp.personne.detail.title">Personne</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personneEntity.id}</dd>
          <dt>
            <span id="nom">
              <Translate contentKey="syndicWebApp.personne.nom">Nom</Translate>
            </span>
          </dt>
          <dd>{personneEntity.nom}</dd>
          <dt>
            <span id="prenom">
              <Translate contentKey="syndicWebApp.personne.prenom">Prenom</Translate>
            </span>
          </dt>
          <dd>{personneEntity.prenom}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="syndicWebApp.personne.email">Email</Translate>
            </span>
          </dt>
          <dd>{personneEntity.email}</dd>
          <dt>
            <span id="motPasse">
              <Translate contentKey="syndicWebApp.personne.motPasse">Mot Passe</Translate>
            </span>
          </dt>
          <dd>{personneEntity.motPasse}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="syndicWebApp.personne.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{personneEntity.adresse}</dd>
          <dt>
            <span id="ville">
              <Translate contentKey="syndicWebApp.personne.ville">Ville</Translate>
            </span>
          </dt>
          <dd>{personneEntity.ville}</dd>
          <dt>
            <span id="photo">
              <Translate contentKey="syndicWebApp.personne.photo">Photo</Translate>
            </span>
          </dt>
          <dd>{personneEntity.photo}</dd>
          <dt>
            <span id="tel">
              <Translate contentKey="syndicWebApp.personne.tel">Tel</Translate>
            </span>
          </dt>
          <dd>{personneEntity.tel}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.personne.internalUser">Internal User</Translate>
          </dt>
          <dd>{personneEntity.internalUser ? personneEntity.internalUser.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/personne" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/personne/${personneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonneDetail;
