import { Moment } from 'moment';
import { IArticle } from 'app/shared/model/article.model';

export interface IHistorique {
  id?: number;
  dateValidation?: Moment;
  clientId?: number;
  articleHistoriques?: IArticle[];
}

export const defaultValue: Readonly<IHistorique> = {};
