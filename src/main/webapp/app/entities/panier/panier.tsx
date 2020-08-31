import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './panier.reducer';
import { IPanier } from 'app/shared/model/panier.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IPanierProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Panier = (props: IPanierProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { panierList, match, loading } = props;
  return (
    <div>
      <h2 id="panier-heading">
        <Translate contentKey="eCommerceJhipsterV1App.panier.home.title">Paniers</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="eCommerceJhipsterV1App.panier.home.createLabel">Create new Panier</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {panierList && panierList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eCommerceJhipsterV1App.panier.dateCreation">Date Creation</Translate>
                </th>
                <th>
                  <Translate contentKey="eCommerceJhipsterV1App.panier.client">Client</Translate>
                </th>
                <th>
                  <Translate contentKey="eCommerceJhipsterV1App.panier.article">Article</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {panierList.map((panier, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${panier.id}`} color="link" size="sm">
                      {panier.id}
                    </Button>
                  </td>
                  <td>
                    <TextFormat type="date" value={panier.dateCreation} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{panier.clientId ? panier.clientId : ''}</td>
                  <td>
                    {panier.articles
                      ? panier.articles.map((val, j) => (
                          <span key={j}>
                            <Link to={`article/${val.id}`}>{val.id}</Link>
                            {j === panier.articles.length - 1 ? '' : ', '}
                          </span>
                        ))
                      : null}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${panier.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${panier.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${panier.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="eCommerceJhipsterV1App.panier.home.notFound">No Paniers found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ panier }: IRootState) => ({
  panierList: panier.entities,
  loading: panier.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Panier);
