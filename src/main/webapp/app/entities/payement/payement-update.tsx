import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAppartement } from 'app/shared/model/appartement.model';
import { getEntities as getAppartements } from 'app/entities/appartement/appartement.reducer';
import { IPayement } from 'app/shared/model/payement.model';
import { getEntity, updateEntity, createEntity, reset } from './payement.reducer';

export const PayementUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const appartements = useAppSelector(state => state.appartement.entities);
  const payementEntity = useAppSelector(state => state.payement.entity);
  const loading = useAppSelector(state => state.payement.loading);
  const updating = useAppSelector(state => state.payement.updating);
  const updateSuccess = useAppSelector(state => state.payement.updateSuccess);
  const handleClose = () => {
    props.history.push('/payement');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAppartements({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...payementEntity,
      ...values,
      appartement: appartements.find(it => it.id.toString() === values.appartement.toString()),
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
          ...payementEntity,
          appartement: payementEntity?.appartement?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.payement.home.createOrEditLabel" data-cy="PayementCreateUpdateHeading">
            <Translate contentKey="syndicWebApp.payement.home.createOrEditLabel">Create or edit a Payement</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="payement-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('syndicWebApp.payement.montant')}
                id="payement-montant"
                name="montant"
                data-cy="montant"
                type="text"
              />
              <ValidatedField label={translate('syndicWebApp.payement.date')} id="payement-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label={translate('syndicWebApp.payement.description')}
                id="payement-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="payement-appartement"
                name="appartement"
                data-cy="appartement"
                label={translate('syndicWebApp.payement.appartement')}
                type="select"
              >
                <option value="" key="0" />
                {appartements
                  ? appartements.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/payement" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PayementUpdate;
