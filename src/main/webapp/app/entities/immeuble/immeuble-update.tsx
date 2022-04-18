import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ISyndic } from 'app/shared/model/syndic.model';
import { getEntities as getSyndics } from 'app/entities/syndic/syndic.reducer';
import { IImmeuble } from 'app/shared/model/immeuble.model';
import { getEntity, updateEntity, createEntity, reset } from './immeuble.reducer';

export const ImmeubleUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const syndics = useAppSelector(state => state.syndic.entities);
  const immeubleEntity = useAppSelector(state => state.immeuble.entity);
  const loading = useAppSelector(state => state.immeuble.loading);
  const updating = useAppSelector(state => state.immeuble.updating);
  const updateSuccess = useAppSelector(state => state.immeuble.updateSuccess);
  const handleClose = () => {
    props.history.push('/immeuble');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getSyndics({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...immeubleEntity,
      ...values,
      syndic: syndics.find(it => it.id.toString() === values.syndic.toString()),
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
          ...immeubleEntity,
          syndic: immeubleEntity?.syndic?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.immeuble.home.createOrEditLabel" data-cy="ImmeubleCreateUpdateHeading">
            Create or edit a Immeuble
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="immeuble-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Libelle" id="immeuble-libelle" name="libelle" data-cy="libelle" type="text" />
              <ValidatedField label="Adresse" id="immeuble-adresse" name="adresse" data-cy="adresse" type="text" />
              <ValidatedField label="Ville" id="immeuble-ville" name="ville" data-cy="ville" type="text" />
              <ValidatedField label="Numero" id="immeuble-numero" name="numero" data-cy="numero" type="text" />
              <ValidatedField label="Nb Etages" id="immeuble-nbEtages" name="nbEtages" data-cy="nbEtages" type="text" />
              <ValidatedField id="immeuble-syndic" name="syndic" data-cy="syndic" label="Syndic" type="select">
                <option value="" key="0" />
                {syndics
                  ? syndics.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/immeuble" replace color="info">
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

export default ImmeubleUpdate;
