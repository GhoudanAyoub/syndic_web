import { IPersonne } from 'app/shared/model/personne.model';
import { IAppartement } from 'app/shared/model/appartement.model';

export interface IResident {
  id?: string;
  etatFamiliale?: string | null;
  personne?: IPersonne | null;
  appartements?: IAppartement[] | null;
}

export const defaultValue: Readonly<IResident> = {};
