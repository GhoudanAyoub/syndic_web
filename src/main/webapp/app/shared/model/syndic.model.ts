import { IPersonne } from 'app/shared/model/personne.model';
import { IImmeuble } from 'app/shared/model/immeuble.model';

export interface ISyndic {
  id?: string;
  salaire?: number | null;
  personne?: IPersonne | null;
  immeubles?: IImmeuble[] | null;
}

export const defaultValue: Readonly<ISyndic> = {};
