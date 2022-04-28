import { ICategorie } from '@/shared/model/categorie.model';
import { IImmeuble } from '@/shared/model/immeuble.model';

export interface IRevenu {
  id?: number;
  montant?: number | null;
  date?: Date | null;
  description?: string | null;
  categories?: ICategorie[] | null;
  immeuble?: IImmeuble | null;
}

export class Revenu implements IRevenu {
  constructor(
    public id?: number,
    public montant?: number | null,
    public date?: Date | null,
    public description?: string | null,
    public categories?: ICategorie[] | null,
    public immeuble?: IImmeuble | null
  ) {}
}
