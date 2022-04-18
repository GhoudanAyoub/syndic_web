import dayjs from 'dayjs';
import { ICategorie } from 'app/shared/model/categorie.model';
import { IImmeuble } from 'app/shared/model/immeuble.model';

export interface IRevenu {
  id?: string;
  montant?: number | null;
  date?: string | null;
  description?: string | null;
  categories?: ICategorie[] | null;
  immeuble?: IImmeuble | null;
}

export const defaultValue: Readonly<IRevenu> = {};
