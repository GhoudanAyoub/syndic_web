import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './appartement.reducer';

export const AppartementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const appartementEntity = useAppSelector(state => state.appartement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appartementDetailsHeading">
          <Translate contentKey="syndicWebApp.appartement.detail.title">Appartement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appartementEntity.id}</dd>
          <dt>
            <span id="numero">
              <Translate contentKey="syndicWebApp.appartement.numero">Numero</Translate>
            </span>
          </dt>
          <dd>{appartementEntity.numero}</dd>
          <dt>
            <span id="etage">
              <Translate contentKey="syndicWebApp.appartement.etage">Etage</Translate>
            </span>
          </dt>
          <dd>{appartementEntity.etage}</dd>
          <dt>
            <span id="surface">
              <Translate contentKey="syndicWebApp.appartement.surface">Surface</Translate>
            </span>
          </dt>
          <dd>{appartementEntity.surface}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.appartement.resident">Resident</Translate>
          </dt>
          <dd>{appartementEntity.resident ? appartementEntity.resident.id : ''}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.appartement.immeuble">Immeuble</Translate>
          </dt>
          <dd>{appartementEntity.immeuble ? appartementEntity.immeuble.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/appartement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/appartement/${appartementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppartementDetail;
