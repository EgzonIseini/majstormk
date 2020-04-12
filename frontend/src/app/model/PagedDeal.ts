import { Listing } from './Listing';
import {Deal} from './Deal';

export interface PagedDeal {
    content: Deal[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
