import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { ITypeArticle } from 'app/shared/model/type-article.model';
import { getEntities as getTypeArticles } from 'app/entities/type-article/type-article.reducer';
import { IPanier } from 'app/shared/model/panier.model';
import { getEntities as getPaniers } from 'app/entities/panier/panier.reducer';
import { IHistorique } from 'app/shared/model/historique.model';
import { getEntities as getHistoriques } from 'app/entities/historique/historique.reducer';
import { getEntity, updateEntity, createEntity, reset } from './article.reducer';
import { IArticle } from 'app/shared/model/article.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IArticleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ArticleUpdate = (props: IArticleUpdateProps) => {
  const [typeArticleId, setTypeArticleId] = useState('0');
  const [panierId, setPanierId] = useState('0');
  const [panierHistoriqueId, setPanierHistoriqueId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { articleEntity, typeArticles, paniers, historiques, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/article');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getTypeArticles();
    props.getPaniers();
    props.getHistoriques();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...articleEntity,
        ...values
      };

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
          <h2 id="eCommerceJhipsterV1App.article.home.createOrEditLabel">
            <Translate contentKey="eCommerceJhipsterV1App.article.home.createOrEditLabel">Create or edit a Article</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : articleEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="article-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="article-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="libelleLabel" for="article-libelle">
                  <Translate contentKey="eCommerceJhipsterV1App.article.libelle">Libelle</Translate>
                </Label>
                <AvField
                  id="article-libelle"
                  type="text"
                  name="libelle"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    minLength: { value: 2, errorMessage: translate('entity.validation.minlength', { min: 2 }) },
                    maxLength: { value: 30, errorMessage: translate('entity.validation.maxlength', { max: 30 }) }
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="prixLabel" for="article-prix">
                  <Translate contentKey="eCommerceJhipsterV1App.article.prix">Prix</Translate>
                </Label>
                <AvField id="article-prix" type="string" className="form-control" name="prix" />
              </AvGroup>
              <AvGroup>
                <Label for="article-typeArticle">
                  <Translate contentKey="eCommerceJhipsterV1App.article.typeArticle">Type Article</Translate>
                </Label>
                <AvInput id="article-typeArticle" type="select" className="form-control" name="typeArticleId">
                  <option value="" key="0" />
                  {typeArticles
                    ? typeArticles.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/article" replace color="info">
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
  typeArticles: storeState.typeArticle.entities,
  paniers: storeState.panier.entities,
  historiques: storeState.historique.entities,
  articleEntity: storeState.article.entity,
  loading: storeState.article.loading,
  updating: storeState.article.updating,
  updateSuccess: storeState.article.updateSuccess
});

const mapDispatchToProps = {
  getTypeArticles,
  getPaniers,
  getHistoriques,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ArticleUpdate);
