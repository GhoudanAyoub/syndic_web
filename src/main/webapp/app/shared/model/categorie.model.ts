import { IDepense } from '@/shared/model/depense.model';
import { IRevenu } from '@/shared/model/revenu.model';

export interface ICategorie {
  id?: number;
  libelle?: string | null;
  depense?: IDepense | null;
  revenu?: IRevenu | null;
}

export class Categorie implements ICategorie {
  constructor(public id?: number, public libelle?: string | null, public depense?: IDepense | null, public revenu?: IRevenu | null) {}
}
