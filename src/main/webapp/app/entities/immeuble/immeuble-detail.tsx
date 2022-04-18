import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
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
        <h2 data-cy="immeubleDetailsHeading">Immeuble</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{immeubleEntity.id}</dd>
          <dt>
            <span id="libelle">Libelle</span>
          </dt>
          <dd>{immeubleEntity.libelle}</dd>
          <dt>
            <span id="adresse">Adresse</span>
          </dt>
          <dd>{immeubleEntity.adresse}</dd>
          <dt>
            <span id="ville">Ville</span>
          </dt>
          <dd>{immeubleEntity.ville}</dd>
          <dt>
            <span id="numero">Numero</span>
          </dt>
          <dd>{immeubleEntity.numero}</dd>
          <dt>
            <span id="nbEtages">Nb Etages</span>
          </dt>
          <dd>{immeubleEntity.nbEtages}</dd>
          <dt>Syndic</dt>
          <dd>{immeubleEntity.syndic ? immeubleEntity.syndic.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/immeuble" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/immeuble/${immeubleEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ImmeubleDetail;
