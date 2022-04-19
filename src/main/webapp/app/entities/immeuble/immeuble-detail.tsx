import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './immeuble.reducer';

export const ImmeubleDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const immeubleEntity = useAppSelector(state => state.immeuble.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="immeubleDetailsHeading">
          <Translate contentKey="syndicWebApp.immeuble.detail.title">Immeuble</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.id}</dd>
          <dt>
            <span id="libelle">
              <Translate contentKey="syndicWebApp.immeuble.libelle">Libelle</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.libelle}</dd>
          <dt>
            <span id="adresse">
              <Translate contentKey="syndicWebApp.immeuble.adresse">Adresse</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.adresse}</dd>
          <dt>
            <span id="ville">
              <Translate contentKey="syndicWebApp.immeuble.ville">Ville</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.ville}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="syndicWebApp.immeuble.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.numero}</dd>
          <dt>
            <span id="nbEtages">
              <Translate contentKey="syndicWebApp.immeuble.nbEtages">Nb Etages</Translate>
            </span>
          </dt>
          <dd>{immeubleEntity.nbEtages}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.immeuble.syndic">Syndic</Translate>
          </dt>
          <dd>{immeubleEntity.syndic ? immeubleEntity.syndic.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/immeuble" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/immeuble/${immeubleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImmeubleDetail;
