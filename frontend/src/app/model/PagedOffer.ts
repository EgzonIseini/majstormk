import {Offer} from './Offer';

export interface PagedOffer {
    content: Offer[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
