import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './depense.reducer';

export const DepenseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const depenseEntity = useAppSelector(state => state.depense.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="depenseDetailsHeading">Depense</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{depenseEntity.id}</dd>
          <dt>
            <span id="montant">Montant</span>
          </dt>
          <dd>{depenseEntity.montant}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{depenseEntity.date ? <TextFormat value={depenseEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{depenseEntity.description}</dd>
          <dt>Immeuble</dt>
          <dd>{depenseEntity.immeuble ? depenseEntity.immeuble.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/depense" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/depense/${depenseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default DepenseDetail;
