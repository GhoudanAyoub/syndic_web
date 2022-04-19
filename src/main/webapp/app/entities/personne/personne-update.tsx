import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IPersonne } from 'app/shared/model/personne.model';
import { getEntity, updateEntity, createEntity, reset } from './personne.reducer';

export const PersonneUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
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

    dispatch(getUsers({}));
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
      internalUser: users.find(it => it.id.toString() === values.internalUser.toString()),
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
          internalUser: personneEntity?.internalUser?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.personne.home.createOrEditLabel" data-cy="PersonneCreateUpdateHeading">
            <Translate contentKey="syndicWebApp.personne.home.createOrEditLabel">Create or edit a Personne</Translate>
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
                  id="personne-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('syndicWebApp.personne.nom')} id="personne-nom" name="nom" data-cy="nom" type="text" />
              <ValidatedField
                label={translate('syndicWebApp.personne.prenom')}
                id="personne-prenom"
                name="prenom"
                data-cy="prenom"
                type="text"
              />
              <ValidatedField
                label={translate('syndicWebApp.personne.email')}
                id="personne-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('syndicWebApp.personne.motPasse')}
                id="personne-motPasse"
                name="motPasse"
                data-cy="motPasse"
                type="text"
              />
              <ValidatedField
                label={translate('syndicWebApp.personne.adresse')}
                id="personne-adresse"
                name="adresse"
                data-cy="adresse"
                type="text"
              />
              <ValidatedField
                label={translate('syndicWebApp.personne.ville')}
                id="personne-ville"
                name="ville"
                data-cy="ville"
                type="text"
              />
              <ValidatedField
                label={translate('syndicWebApp.personne.photo')}
                id="personne-photo"
                name="photo"
                data-cy="photo"
                type="text"
              />
              <ValidatedField label={translate('syndicWebApp.personne.tel')} id="personne-tel" name="tel" data-cy="tel" type="text" />
              <ValidatedField
                id="personne-internalUser"
                name="internalUser"
                data-cy="internalUser"
                label={translate('syndicWebApp.personne.internalUser')}
                type="select"
              >
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/personne" replace color="info">
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

export default PersonneUpdate;
