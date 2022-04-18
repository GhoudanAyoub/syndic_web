import { IPayement } from 'app/shared/model/payement.model';
import { IResident } from 'app/shared/model/resident.model';
import { IImmeuble } from 'app/shared/model/immeuble.model';

export interface IAppartement {
  id?: string;
  numero?: number | null;
  etage?: number | null;
  surface?: number | null;
  payements?: IPayement[] | null;
  resident?: IResident | null;
  immeuble?: IImmeuble | null;
}

export const defaultValue: Readonly<IAppartement> = {};
