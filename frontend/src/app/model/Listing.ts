import { City } from './City';
import {PrivateUser} from './users/PrivateUser';
import {Deal} from './Deal';

export interface Listing {
    id: number;
    name: string;
    userId: PrivateUser;
    description: string;
    dueDate: Date;
    city: City;
    active: boolean;
    price: number;
    deal: Deal;
}
