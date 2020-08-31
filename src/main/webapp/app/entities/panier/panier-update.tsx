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
import { getEntity, updateEntity, createEntity, reset } from './panier.reducer';
import { IPanier } from 'app/shared/model/panier.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IPanierUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PanierUpdate = (props: IPanierUpdateProps) => {
  const [idsarticle, setIdsarticle] = useState([]);
  const [clientId, setClientId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { panierEntity, users, articles, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/panier');
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
    values.dateCreation = convertDateTimeToServer(values.dateCreation);

    if (errors.length === 0) {
      const entity = {
        ...panierEntity,
        ...values,
        articles: mapIdList(values.articles)
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
          <h2 id="eCommerceJhipsterV1App.panier.home.createOrEditLabel">
            <Translate contentKey="eCommerceJhipsterV1App.panier.home.createOrEditLabel">Create or edit a Panier</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : panierEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="panier-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="panier-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateCreationLabel" for="panier-dateCreation">
                  <Translate contentKey="eCommerceJhipsterV1App.panier.dateCreation">Date Creation</Translate>
                </Label>
                <AvInput
                  id="panier-dateCreation"
                  type="datetime-local"
                  className="form-control"
                  name="dateCreation"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.panierEntity.dateCreation)}
                />
              </AvGroup>
              <AvGroup>
                <Label for="panier-client">
                  <Translate contentKey="eCommerceJhipsterV1App.panier.client">Client</Translate>
                </Label>
                <AvInput id="panier-client" type="select" className="form-control" name="clientId">
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
                <Label for="panier-article">
                  <Translate contentKey="eCommerceJhipsterV1App.panier.article">Article</Translate>
                </Label>
                <AvInput
                  id="panier-article"
                  type="select"
                  multiple
                  className="form-control"
                  name="articles"
                  value={panierEntity.articles && panierEntity.articles.map(e => e.id)}
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
              <Button tag={Link} id="cancel-save" to="/panier" replace color="info">
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
  panierEntity: storeState.panier.entity,
  loading: storeState.panier.loading,
  updating: storeState.panier.updating,
  updateSuccess: storeState.panier.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(PanierUpdate);
