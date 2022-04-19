import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IPersonne } from 'app/shared/model/personne.model';
import { getEntities as getPersonnes } from 'app/entities/personne/personne.reducer';
import { ISyndic } from 'app/shared/model/syndic.model';
import { getEntity, updateEntity, createEntity, reset } from './syndic.reducer';

export const SyndicUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const personnes = useAppSelector(state => state.personne.entities);
  const syndicEntity = useAppSelector(state => state.syndic.entity);
  const loading = useAppSelector(state => state.syndic.loading);
  const updating = useAppSelector(state => state.syndic.updating);
  const updateSuccess = useAppSelector(state => state.syndic.updateSuccess);
  const handleClose = () => {
    props.history.push('/syndic');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPersonnes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...syndicEntity,
      ...values,
      personne: personnes.find(it => it.id.toString() === values.personne.toString()),
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
          ...syndicEntity,
          personne: syndicEntity?.personne?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.syndic.home.createOrEditLabel" data-cy="SyndicCreateUpdateHeading">
            <Translate contentKey="syndicWebApp.syndic.home.createOrEditLabel">Create or edit a Syndic</Translate>
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
                  id="syndic-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('syndicWebApp.syndic.salaire')}
                id="syndic-salaire"
                name="salaire"
                data-cy="salaire"
                type="text"
              />
              <ValidatedField
                id="syndic-personne"
                name="personne"
                data-cy="personne"
                label={translate('syndicWebApp.syndic.personne')}
                type="select"
              >
                <option value="" key="0" />
                {personnes
                  ? personnes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/syndic" replace color="info">
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

export default SyndicUpdate;
