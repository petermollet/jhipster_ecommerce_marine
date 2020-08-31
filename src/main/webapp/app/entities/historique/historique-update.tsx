import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IArticle } from 'app/shared/model/article.model';
import { getEntities as getArticles } from 'app/entities/article/article.reducer';
import { getEntity, updateEntity, createEntity, reset } from './historique.reducer';
import { IHistorique } from 'app/shared/model/historique.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IHistoriqueUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HistoriqueUpdate = (props: IHistoriqueUpdateProps) => {
  const [idsarticleHistorique, setIdsarticleHistorique] = useState([]);
  const [clientId, setClientId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { historiqueEntity, users, articles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/historique' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getUsers();
    props.getArticles();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.dateValidation = convertDateTimeToServer(values.dateValidation);

    if (errors.length === 0) {
      const entity = {
        ...historiqueEntity,
        ...values,
        articleHistoriques: mapIdList(values.articleHistoriques)
      };
      entity.user = users[values.user];

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="eCommerceJhipsterV1App.historique.home.createOrEditLabel">
            <Translate contentKey="eCommerceJhipsterV1App.historique.home.createOrEditLabel">Create or edit a Historique</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : historiqueEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="historique-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="historique-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateValidationLabel" for="historique-dateValidation">
                  <Translate contentKey="eCommerceJhipsterV1App.historique.dateValidation">Date Validation</Translate>
                </Label>
                <AvInput
                  id="historique-dateValidation"
                  type="datetime-local"
                  className="form-control"
                  name="dateValidation"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.historiqueEntity.dateValidation)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="historique-client">
                  <Translate contentKey="eCommerceJhipsterV1App.historique.client">Client</Translate>
                </Label>
                <AvInput id="historique-client" type="select" className="form-control" name="clientId">
                  <option value="" key="0" />
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label for="historique-articleHistorique">
                  <Translate contentKey="eCommerceJhipsterV1App.historique.articleHistorique">Article Historique</Translate>
                </Label>
                <AvInput
                  id="historique-articleHistorique"
                  type="select"
                  multiple
                  className="form-control"
                  name="articleHistoriques"
                  value={historiqueEntity.articleHistoriques && historiqueEntity.articleHistoriques.map(e => e.id)}
                >
                  <option value="" key="0" />
                  {articles
                    ? articles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/historique" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  users: storeState.userManagement.users,
  articles: storeState.article.entities,
  historiqueEntity: storeState.historique.entity,
  loading: storeState.historique.loading,
  updating: storeState.historique.updating,
  updateSuccess: storeState.historique.updateSuccess
});

const mapDispatchToProps = {
  getUsers,
  getArticles,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HistoriqueUpdate);
