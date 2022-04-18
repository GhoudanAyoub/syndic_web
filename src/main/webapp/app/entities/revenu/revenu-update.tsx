import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IImmeuble } from 'app/shared/model/immeuble.model';
import { getEntities as getImmeubles } from 'app/entities/immeuble/immeuble.reducer';
import { IRevenu } from 'app/shared/model/revenu.model';
import { getEntity, updateEntity, createEntity, reset } from './revenu.reducer';

export const RevenuUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const immeubles = useAppSelector(state => state.immeuble.entities);
  const revenuEntity = useAppSelector(state => state.revenu.entity);
  const loading = useAppSelector(state => state.revenu.loading);
  const updating = useAppSelector(state => state.revenu.updating);
  const updateSuccess = useAppSelector(state => state.revenu.updateSuccess);
  const handleClose = () => {
    props.history.push('/revenu');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getImmeubles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...revenuEntity,
      ...values,
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
          ...revenuEntity,
          immeuble: revenuEntity?.immeuble?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.revenu.home.createOrEditLabel" data-cy="RevenuCreateUpdateHeading">
            Create or edit a Revenu
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="revenu-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Montant" id="revenu-montant" name="montant" data-cy="montant" type="text" />
              <ValidatedField label="Date" id="revenu-date" name="date" data-cy="date" type="date" />
              <ValidatedField label="Description" id="revenu-description" name="description" data-cy="description" type="text" />
              <ValidatedField id="revenu-immeuble" name="immeuble" data-cy="immeuble" label="Immeuble" type="select">
                <option value="" key="0" />
                {immeubles
                  ? immeubles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/revenu" replace color="info">
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

export default RevenuUpdate;
