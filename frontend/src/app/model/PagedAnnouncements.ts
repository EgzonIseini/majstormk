import {TransformedAnnouncement} from './Announcement';

export interface PagedAnnouncements {
    content: TransformedAnnouncement[];
    last: boolean;
    totalPages: number;
    totalElements: number;
    size: number;
    first: boolean;
    numberOfElements: number;
    number: number;
}
