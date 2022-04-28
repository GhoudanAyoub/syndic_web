import { IAppartement } from '@/shared/model/appartement.model';
import { ISyndic } from '@/shared/model/syndic.model';
import { IDepense } from '@/shared/model/depense.model';
import { IRevenu } from '@/shared/model/revenu.model';

export interface IImmeuble {
  id?: number;
  libelle?: string | null;
  adresse?: string | null;
  ville?: string | null;
  numero?: number | null;
  nbEtages?: number | null;
  photoContentType?: string | null;
  photo?: string | null;
  appartements?: IAppartement[] | null;
  syndic?: ISyndic | null;
  depenses?: IDepense[] | null;
  revenus?: IRevenu[] | null;
}

export class Immeuble implements IImmeuble {
  constructor(
    public id?: number,
    public libelle?: string | null,
    public adresse?: string | null,
    public ville?: string | null,
    public numero?: number | null,
    public nbEtages?: number | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public appartements?: IAppartement[] | null,
    public syndic?: ISyndic | null,
    public depenses?: IDepense[] | null,
    public revenus?: IRevenu[] | null
  ) {}
}
