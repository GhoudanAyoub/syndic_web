import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDepense } from 'app/shared/model/depense.model';
import { getEntities as getDepenses } from 'app/entities/depense/depense.reducer';
import { IRevenu } from 'app/shared/model/revenu.model';
import { getEntities as getRevenus } from 'app/entities/revenu/revenu.reducer';
import { ICategorie } from 'app/shared/model/categorie.model';
import { getEntity, updateEntity, createEntity, reset } from './categorie.reducer';

export const CategorieUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const depenses = useAppSelector(state => state.depense.entities);
  const revenus = useAppSelector(state => state.revenu.entities);
  const categorieEntity = useAppSelector(state => state.categorie.entity);
  const loading = useAppSelector(state => state.categorie.loading);
  const updating = useAppSelector(state => state.categorie.updating);
  const updateSuccess = useAppSelector(state => state.categorie.updateSuccess);
  const handleClose = () => {
    props.history.push('/categorie');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getDepenses({}));
    dispatch(getRevenus({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...categorieEntity,
      ...values,
      depense: depenses.find(it => it.id.toString() === values.depense.toString()),
      revenu: revenus.find(it => it.id.toString() === values.revenu.toString()),
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
          ...categorieEntity,
          depense: categorieEntity?.depense?.id,
          revenu: categorieEntity?.revenu?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="syndicWebApp.categorie.home.createOrEditLabel" data-cy="CategorieCreateUpdateHeading">
            Create or edit a Categorie
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="categorie-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Libelle" id="categorie-libelle" name="libelle" data-cy="libelle" type="text" />
              <ValidatedField id="categorie-depense" name="depense" data-cy="depense" label="Depense" type="select">
                <option value="" key="0" />
                {depenses
                  ? depenses.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="categorie-revenu" name="revenu" data-cy="revenu" label="Revenu" type="select">
                <option value="" key="0" />
                {revenus
                  ? revenus.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/categorie" replace color="info">
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

export default CategorieUpdate;
