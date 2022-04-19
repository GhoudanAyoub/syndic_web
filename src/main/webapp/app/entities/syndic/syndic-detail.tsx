import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './syndic.reducer';

export const SyndicDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const syndicEntity = useAppSelector(state => state.syndic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="syndicDetailsHeading">
          <Translate contentKey="syndicWebApp.syndic.detail.title">Syndic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{syndicEntity.id}</dd>
          <dt>
            <span id="salaire">
              <Translate contentKey="syndicWebApp.syndic.salaire">Salaire</Translate>
            </span>
          </dt>
          <dd>{syndicEntity.salaire}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.syndic.personne">Personne</Translate>
          </dt>
          <dd>{syndicEntity.personne ? syndicEntity.personne.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/syndic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/syndic/${syndicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SyndicDetail;
