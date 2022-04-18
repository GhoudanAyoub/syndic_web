import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
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
        <h2 data-cy="appartementDetailsHeading">Appartement</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{appartementEntity.id}</dd>
          <dt>
            <span id="numero">Numero</span>
          </dt>
          <dd>{appartementEntity.numero}</dd>
          <dt>
            <span id="etage">Etage</span>
          </dt>
          <dd>{appartementEntity.etage}</dd>
          <dt>
            <span id="surface">Surface</span>
          </dt>
          <dd>{appartementEntity.surface}</dd>
          <dt>Resident</dt>
          <dd>{appartementEntity.resident ? appartementEntity.resident.id : ''}</dd>
          <dt>Immeuble</dt>
          <dd>{appartementEntity.immeuble ? appartementEntity.immeuble.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/appartement" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/appartement/${appartementEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppartementDetail;
