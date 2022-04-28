import { IAppartement } from '@/shared/model/appartement.model';

export interface IPayement {
  id?: number;
  montant?: number | null;
  date?: Date | null;
  description?: string | null;
  appartement?: IAppartement | null;
}

export class Payement implements IPayement {
  constructor(
    public id?: number,
    public montant?: number | null,
    public date?: Date | null,
    public description?: string | null,
    public appartement?: IAppartement | null
  ) {}
}
