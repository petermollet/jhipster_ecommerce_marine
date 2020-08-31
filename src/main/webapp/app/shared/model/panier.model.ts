import { Moment } from 'moment';
import { IArticle } from 'app/shared/model/article.model';

export interface IPanier {
  id?: number;
  dateCreation?: Moment;
  clientId?: number;
  articles?: IArticle[];
}

export const defaultValue: Readonly<IPanier> = {};
