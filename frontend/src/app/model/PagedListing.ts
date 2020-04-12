import { Listing } from './Listing';

export interface PagedListing {
    content: Listing[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
