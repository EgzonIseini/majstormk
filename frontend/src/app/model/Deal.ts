import {PrivateUser} from './users/PrivateUser';
import {Listing} from './Listing';

export interface Deal {
    id: number;
    user: PrivateUser;
    company: PrivateUser;
    listing: Listing;
    price: number;
    date: Date;
}

export interface DealDto {
    listingId: number;
    userId: number;
    madeById: number;
    price: number;
}
