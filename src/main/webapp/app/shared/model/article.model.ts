import { IPanier } from 'app/shared/model/panier.model';
import { IHistorique } from 'app/shared/model/historique.model';

export interface IArticle {
  id?: number;
  libelle?: string;
  prix?: number;
  typeArticleId?: number;
  paniers?: IPanier[];
  panierHistoriques?: IHistorique[];
}

export const defaultValue: Readonly<IArticle> = {};
