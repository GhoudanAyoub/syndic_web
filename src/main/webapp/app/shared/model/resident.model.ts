import { IAppartement } from '@/shared/model/appartement.model';

export interface IResident {
  id?: number;
  email?: string | null;
  motPasse?: string | null;
  nom?: string | null;
  prenom?: string | null;
  adresse?: string | null;
  tel?: string | null;
  appartements?: IAppartement[] | null;
}

export class Resident implements IResident {
  constructor(
    public id?: number,
    public email?: string | null,
    public motPasse?: string | null,
    public nom?: string | null,
    public prenom?: string | null,
    public adresse?: string | null,
    public tel?: string | null,
    public appartements?: IAppartement[] | null
  ) {}
}
