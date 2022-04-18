import { IResident } from 'app/shared/model/resident.model';
import { ISyndic } from 'app/shared/model/syndic.model';

export interface IPersonne {
  id?: string;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  motPasse?: string | null;
  adresse?: string | null;
  resident?: IResident | null;
  syndic?: ISyndic | null;
}

export const defaultValue: Readonly<IPersonne> = {};
