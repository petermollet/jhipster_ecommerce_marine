import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Panier from './panier';
import Article from './article';
import TypeArticle from './type-article';
import Historique from './historique';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}panier`} component={Panier} />
      <ErrorBoundaryRoute path={`${match.url}article`} component={Article} />
      <ErrorBoundaryRoute path={`${match.url}type-article`} component={TypeArticle} />
      <ErrorBoundaryRoute path={`${match.url}historique`} component={Historique} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
