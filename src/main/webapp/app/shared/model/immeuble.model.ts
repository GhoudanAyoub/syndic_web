import { IAppartement } from 'app/shared/model/appartement.model';
import { ISyndic } from 'app/shared/model/syndic.model';
import { IDepense } from 'app/shared/model/depense.model';
import { IRevenu } from 'app/shared/model/revenu.model';

export interface IImmeuble {
  id?: string;
  libelle?: string | null;
  adresse?: string | null;
  ville?: string | null;
  numero?: number | null;
  nbEtages?: number | null;
  appartements?: IAppartement[] | null;
  syndic?: ISyndic | null;
  depenses?: IDepense[] | null;
  revenus?: IRevenu[] | null;
}

export const defaultValue: Readonly<IImmeuble> = {};
