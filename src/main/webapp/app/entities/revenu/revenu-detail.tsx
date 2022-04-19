import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './revenu.reducer';

export const RevenuDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const revenuEntity = useAppSelector(state => state.revenu.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="revenuDetailsHeading">
          <Translate contentKey="syndicWebApp.revenu.detail.title">Revenu</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{revenuEntity.id}</dd>
          <dt>
            <span id="montant">
              <Translate contentKey="syndicWebApp.revenu.montant">Montant</Translate>
            </span>
          </dt>
          <dd>{revenuEntity.montant}</dd>
          <dt>
            <span id="date">
              <Translate contentKey="syndicWebApp.revenu.date">Date</Translate>
            </span>
          </dt>
          <dd>{revenuEntity.date ? <TextFormat value={revenuEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="syndicWebApp.revenu.description">Description</Translate>
            </span>
          </dt>
          <dd>{revenuEntity.description}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.revenu.immeuble">Immeuble</Translate>
          </dt>
          <dd>{revenuEntity.immeuble ? revenuEntity.immeuble.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/revenu" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/revenu/${revenuEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RevenuDetail;
