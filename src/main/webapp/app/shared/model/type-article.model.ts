import { IArticle } from 'app/shared/model/article.model';

export interface ITypeArticle {
  id?: number;
  libelle?: string;
  articles?: IArticle[];
}

export const defaultValue: Readonly<ITypeArticle> = {};
