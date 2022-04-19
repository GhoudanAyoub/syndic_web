import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IImmeuble } from 'app/shared/model/immeuble.model';
import { getEntities as getImmeubles } from 'app/entities/immeuble/immeuble.reducer';
import { IDepense } from 'app/shared/model/depense.model';
import { getEntity, updateEntity, createEntity, reset } from './depense.reducer';

export const DepenseUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const immeubles = useAppSelector(state => state.immeuble.entities);
  const depenseEntity = useAppSelector(state => state.depense.entity);
  const loading = useAppSelector(state => state.depense.loading);
  const updating = useAppSelector(state => state.depense.updating);
  const updateSuccess = useAppSelector(state => state.depense.updateSuccess);
  const handleClose = () => {
    props.history.push('/depense');
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
      ...depenseEntity,
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
          ...depenseEntity,
          immeuble: depenseEntity?.immeuble?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.depense.home.createOrEditLabel" data-cy="DepenseCreateUpdateHeading">
            <Translate contentKey="syndicWebApp.depense.home.createOrEditLabel">Create or edit a Depense</Translate>
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
                  id="depense-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('syndicWebApp.depense.montant')}
                id="depense-montant"
                name="montant"
                data-cy="montant"
                type="text"
              />
              <ValidatedField label={translate('syndicWebApp.depense.date')} id="depense-date" name="date" data-cy="date" type="date" />
              <ValidatedField
                label={translate('syndicWebApp.depense.description')}
                id="depense-description"
                name="description"
                data-cy="description"
                type="text"
              />
              <ValidatedField
                id="depense-immeuble"
                name="immeuble"
                data-cy="immeuble"
                label={translate('syndicWebApp.depense.immeuble')}
                type="select"
              >
                <option value="" key="0" />
                {immeubles
                  ? immeubles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/depense" replace color="info">
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

export default DepenseUpdate;
