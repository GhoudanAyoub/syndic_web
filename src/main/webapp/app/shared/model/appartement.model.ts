import { IPayement } from '@/shared/model/payement.model';
import { IResident } from '@/shared/model/resident.model';
import { IImmeuble } from '@/shared/model/immeuble.model';

export interface IAppartement {
  id?: number;
  numero?: number | null;
  etage?: number | null;
  surface?: number | null;
  payements?: IPayement[] | null;
  resident?: IResident | null;
  immeuble?: IImmeuble | null;
}

export class Appartement implements IAppartement {
  constructor(
    public id?: number,
    public numero?: number | null,
    public etage?: number | null,
    public surface?: number | null,
    public payements?: IPayement[] | null,
    public resident?: IResident | null,
    public immeuble?: IImmeuble | null
  ) {}
}
