import {User} from './User';

export interface CompanyUser extends User {
    id: number;
    password: string;
    emailAddress: string;
    phoneNumber: string;
    address: string;
    city: string;
    zipCode: number;
    name: string;
    description: string;
}
