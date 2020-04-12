export interface Announcement {
    announcement: string;
    entityId: number;
    announcementType: AnnouncementType;
    timestamp: string;
}

export interface TransformedAnnouncement {
    message: string;
    urlId: number;
    backgroundColor: string;
    iconHTML: string;
    timestamp: Date;
    entityId: number;
}

export enum AnnouncementType {
    GLOBAL_NEW_LISTING = 'GLOBAL_NEW_LISTING',
    GLOBAL_DEAL_REACHED = 'GLOBAL_DEAL_REACHED',
    GLOBAL_NEW_OFFER = 'GLOBAL_NEW_OFFER'
}
