import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './type-article.reducer';
import { ITypeArticle } from 'app/shared/model/type-article.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITypeArticleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const TypeArticle = (props: ITypeArticleProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { typeArticleList, match, loading } = props;
  return (
    <div>
      <h2 id="type-article-heading">
        <Translate contentKey="eCommerceJhipsterV1App.typeArticle.home.title">Type Articles</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="eCommerceJhipsterV1App.typeArticle.home.createLabel">Create new Type Article</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {typeArticleList && typeArticleList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="eCommerceJhipsterV1App.typeArticle.libelle">Libelle</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {typeArticleList.map((typeArticle, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${typeArticle.id}`} color="link" size="sm">
                      {typeArticle.id}
                    </Button>
                  </td>
                  <td>{typeArticle.libelle}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${typeArticle.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${typeArticle.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${typeArticle.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="eCommerceJhipsterV1App.typeArticle.home.notFound">No Type Articles found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ typeArticle }: IRootState) => ({
  typeArticleList: typeArticle.entities,
  loading: typeArticle.loading
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TypeArticle);
