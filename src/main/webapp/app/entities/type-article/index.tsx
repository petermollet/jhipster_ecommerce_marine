import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TypeArticle from './type-article';
import TypeArticleDetail from './type-article-detail';
import TypeArticleUpdate from './type-article-update';
import TypeArticleDeleteDialog from './type-article-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TypeArticleDeleteDialog} />
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TypeArticleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TypeArticleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TypeArticleDetail} />
      <ErrorBoundaryRoute path={match.url} component={TypeArticle} />
    </Switch>
  </>
);

export default Routes;
