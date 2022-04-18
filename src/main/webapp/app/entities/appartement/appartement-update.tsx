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
import { IImmeuble } from 'app/shared/model/immeuble.model';
import { getEntities as getImmeubles } from 'app/entities/immeuble/immeuble.reducer';
import { IAppartement } from 'app/shared/model/appartement.model';
import { getEntity, updateEntity, createEntity, reset } from './appartement.reducer';

export const AppartementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const residents = useAppSelector(state => state.resident.entities);
  const immeubles = useAppSelector(state => state.immeuble.entities);
  const appartementEntity = useAppSelector(state => state.appartement.entity);
  const loading = useAppSelector(state => state.appartement.loading);
  const updating = useAppSelector(state => state.appartement.updating);
  const updateSuccess = useAppSelector(state => state.appartement.updateSuccess);
  const handleClose = () => {
    props.history.push('/appartement');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getResidents({}));
    dispatch(getImmeubles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appartementEntity,
      ...values,
      resident: residents.find(it => it.id.toString() === values.resident.toString()),
      immeuble: immeubles.find(it => it.id.toString() === values.immeuble.toString()),
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
          ...appartementEntity,
          resident: appartementEntity?.resident?.id,
          immeuble: appartementEntity?.immeuble?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.appartement.home.createOrEditLabel" data-cy="AppartementCreateUpdateHeading">
            Create or edit a Appartement
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="appartement-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Numero" id="appartement-numero" name="numero" data-cy="numero" type="text" />
              <ValidatedField label="Etage" id="appartement-etage" name="etage" data-cy="etage" type="text" />
              <ValidatedField label="Surface" id="appartement-surface" name="surface" data-cy="surface" type="text" />
              <ValidatedField id="appartement-resident" name="resident" data-cy="resident" label="Resident" type="select">
                <option value="" key="0" />
                {residents
                  ? residents.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="appartement-immeuble" name="immeuble" data-cy="immeuble" label="Immeuble" type="select">
                <option value="" key="0" />
                {immeubles
                  ? immeubles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/appartement" replace color="info">
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

export default AppartementUpdate;
