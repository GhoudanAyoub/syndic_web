import { IUser } from 'app/shared/model/user.model';

export interface IPersonne {
  id?: string;
  nom?: string | null;
  prenom?: string | null;
  email?: string | null;
  motPasse?: string | null;
  adresse?: string | null;
  ville?: string | null;
  photo?: string | null;
  tel?: string | null;
  internalUser?: IUser | null;
}

export const defaultValue: Readonly<IPersonne> = {};
