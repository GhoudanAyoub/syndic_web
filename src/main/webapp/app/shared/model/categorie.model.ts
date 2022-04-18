import { IDepense } from 'app/shared/model/depense.model';
import { IRevenu } from 'app/shared/model/revenu.model';

export interface ICategorie {
  id?: string;
  libelle?: string | null;
  depense?: IDepense | null;
  revenu?: IRevenu | null;
}

export const defaultValue: Readonly<ICategorie> = {};
