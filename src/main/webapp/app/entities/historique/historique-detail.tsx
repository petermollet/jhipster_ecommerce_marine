import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './historique.reducer';
import { IHistorique } from 'app/shared/model/historique.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IHistoriqueDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const HistoriqueDetail = (props: IHistoriqueDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { historiqueEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="eCommerceJhipsterV1App.historique.detail.title">Historique</Translate> [<b>{historiqueEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="dateValidation">
              <Translate contentKey="eCommerceJhipsterV1App.historique.dateValidation">Date Validation</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={historiqueEntity.dateValidation} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <Translate contentKey="eCommerceJhipsterV1App.historique.client">Client</Translate>
          </dt>
          <dd>{historiqueEntity.clientId ? historiqueEntity.clientId : ''}</dd>
          <dt>
            <Translate contentKey="eCommerceJhipsterV1App.historique.articleHistorique">Article Historique</Translate>
          </dt>
          <dd>
            {historiqueEntity.articleHistoriques
              ? historiqueEntity.articleHistoriques.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {i === historiqueEntity.articleHistoriques.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/historique" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/historique/${historiqueEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ historique }: IRootState) => ({
  historiqueEntity: historique.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(HistoriqueDetail);
