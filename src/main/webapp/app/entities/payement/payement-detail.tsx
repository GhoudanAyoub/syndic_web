import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './payement.reducer';

export const PayementDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const payementEntity = useAppSelector(state => state.payement.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="payementDetailsHeading">
          <Translate contentKey="syndicWebApp.payement.detail.title">Payement</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{payementEntity.id}</dd>
          <dt>
            <span id="montant">
              <Translate contentKey="syndicWebApp.payement.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{payementEntity.montant}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="syndicWebApp.payement.date">Date</Translate>
            </span>
          </dt>
          <dd>{payementEntity.date ? <TextFormat value={payementEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="syndicWebApp.payement.description">Description</Translate>
            </span>
          </dt>
          <dd>{payementEntity.description}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.payement.appartement">Appartement</Translate>
          </dt>
          <dd>{payementEntity.appartement ? payementEntity.appartement.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/payement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/payement/${payementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PayementDetail;
