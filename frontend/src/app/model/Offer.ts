import {Listing} from './Listing';
import {PrivateUser} from './users/PrivateUser';

export interface Offer {
    id: number;
    madeBy: PrivateUser;
    description: string;
    wasAccepted: boolean;
    listing: Listing;
    offeredPrice: number;
}
