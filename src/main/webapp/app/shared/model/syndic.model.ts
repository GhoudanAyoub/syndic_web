import { IUser } from '@/shared/model/user.model';
import { IImmeuble } from '@/shared/model/immeuble.model';

export interface ISyndic {
  id?: number;
  adresse?: string | null;
  tel?: string | null;
  dateTravail?: Date | null;
  photoContentType?: string | null;
  photo?: string | null;
  user?: IUser | null;
  immeubles?: IImmeuble[] | null;
}

export class Syndic implements ISyndic {
  constructor(
    public id?: number,
    public adresse?: string | null,
    public tel?: string | null,
    public dateTravail?: Date | null,
    public photoContentType?: string | null,
    public photo?: string | null,
    public user?: IUser | null,
    public immeubles?: IImmeuble[] | null
  ) {}
}
