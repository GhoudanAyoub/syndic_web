import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './resident.reducer';

export const ResidentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const residentEntity = useAppSelector(state => state.resident.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="residentDetailsHeading">
          <Translate contentKey="syndicWebApp.resident.detail.title">Resident</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{residentEntity.id}</dd>
          <dt>
            <span id="etatFamiliale">
              <Translate contentKey="syndicWebApp.resident.etatFamiliale">Etat Familiale</Translate>
            </span>
          </dt>
          <dd>{residentEntity.etatFamiliale}</dd>
          <dt>
            <Translate contentKey="syndicWebApp.resident.personne">Personne</Translate>
          </dt>
          <dd>{residentEntity.personne ? residentEntity.personne.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/resident" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/resident/${residentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ResidentDetail;
