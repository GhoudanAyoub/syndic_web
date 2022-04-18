import dayjs from 'dayjs';
import { IAppartement } from 'app/shared/model/appartement.model';

export interface IPayement {
  id?: string;
  montant?: number | null;
  date?: string | null;
  description?: string | null;
  appartement?: IAppartement | null;
}

export const defaultValue: Readonly<IPayement> = {};
