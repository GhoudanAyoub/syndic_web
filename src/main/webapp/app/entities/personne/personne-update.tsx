import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IResident } from 'app/shared/model/resident.model';
import { getEntities as getResidents } from 'app/entities/resident/resident.reducer';
import { ISyndic } from 'app/shared/model/syndic.model';
import { getEntities as getSyndics } from 'app/entities/syndic/syndic.reducer';
import { IPersonne } from 'app/shared/model/personne.model';
import { getEntity, updateEntity, createEntity, reset } from './personne.reducer';

export const PersonneUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const residents = useAppSelector(state => state.resident.entities);
  const syndics = useAppSelector(state => state.syndic.entities);
  const personneEntity = useAppSelector(state => state.personne.entity);
  const loading = useAppSelector(state => state.personne.loading);
  const updating = useAppSelector(state => state.personne.updating);
  const updateSuccess = useAppSelector(state => state.personne.updateSuccess);
  const handleClose = () => {
    props.history.push('/personne');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResidents({}));
    dispatch(getSyndics({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...personneEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...personneEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.personne.home.createOrEditLabel" data-cy="PersonneCreateUpdateHeading">
            Create or edit a Personne
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="personne-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Nom" id="personne-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField label="Prenom" id="personne-prenom" name="prenom" data-cy="prenom" type="text" />
              <ValidatedField label="Email" id="personne-email" name="email" data-cy="email" type="text" />
              <ValidatedField label="Mot Passe" id="personne-motPasse" name="motPasse" data-cy="motPasse" type="text" />
              <ValidatedField label="Adresse" id="personne-adresse" name="adresse" data-cy="adresse" type="text" />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/personne" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PersonneUpdate;
