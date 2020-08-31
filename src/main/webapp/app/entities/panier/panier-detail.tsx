import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './panier.reducer';
import { IPanier } from 'app/shared/model/panier.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPanierDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const PanierDetail = (props: IPanierDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { panierEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="eCommerceJhipsterV1App.panier.detail.title">Panier</Translate> [<b>{panierEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="dateCreation">
              <Translate contentKey="eCommerceJhipsterV1App.panier.dateCreation">Date Creation</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={panierEntity.dateCreation} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="eCommerceJhipsterV1App.panier.client">Client</Translate>
          </dt>
          <dd>{panierEntity.clientId ? panierEntity.clientId : ''}</dd>
          <dt>
            <Translate contentKey="eCommerceJhipsterV1App.panier.article">Article</Translate>
          </dt>
          <dd>
            {panierEntity.articles
              ? panierEntity.articles.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === panierEntity.articles.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/panier" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/panier/${panierEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ panier }: IRootState) => ({
  panierEntity: panier.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(PanierDetail);
