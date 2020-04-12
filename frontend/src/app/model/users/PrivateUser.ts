import {User} from './User';

export interface PrivateUser extends User {
    id: number;
    firstName: string;
    lastName: string;
    password: string;
    emailAddress: string;
    dateOfBirth: Date;
    phoneNumber: string;
    address: string;
    city: string;
    zipCode: number;
}
