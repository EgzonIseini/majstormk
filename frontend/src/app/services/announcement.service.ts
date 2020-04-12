import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Announcement, AnnouncementType, TransformedAnnouncement} from '../model/Announcement';
import {map} from 'rxjs/operators';
import {PagedDeal} from '../model/PagedDeal';
import {PagedAnnouncements} from '../model/PagedAnnouncements';

@Injectable({
    providedIn: 'root'
})
export class AnnouncementService {

    constructor(
        private http: HttpClient
    ) { }

    public getLatest5GlobalAnnouncements(): Observable<TransformedAnnouncement[]> {
        return this.http.get<Announcement[]>('/api/announcements/global/latest')
            .pipe(map(elem => this.transformAnnouncements(elem)));
    }

    public getAllGlobalAnnouncements(
        pageNo: number = 0,
        pageSize: number = 10): Observable<PagedAnnouncements> {
        return this.http.get(`/api/announcements/global?pageNo=${pageNo}&pageSize=${pageSize}`)
            .pipe(map((elem: any) => {
                elem.content = this.transformAnnouncements(elem.content);
                return elem;
            }));
    }

    private transformAnnouncements(announcements: Announcement[]): TransformedAnnouncement[] {
        if (announcements === [] || announcements === undefined) {
            return [];
        } else {
            return announcements.map((value) => {
                let iconHTMLText;
                let backgroundColorText;
                const announcementType = AnnouncementType[value.announcementType];
                if (announcementType === AnnouncementType.GLOBAL_NEW_LISTING) {
                    iconHTMLText = '<i class="fa fa-align-justify text-white"></i>';
                    backgroundColorText = 'bg-primary';
                } else if (announcementType === AnnouncementType.GLOBAL_DEAL_REACHED) {
                    iconHTMLText = '<i class="fa fa-handshake text-white"></i>';
                    backgroundColorText = 'bg-success';
                } else if (announcementType === AnnouncementType.GLOBAL_NEW_OFFER) {
                    iconHTMLText = '<i class="fa fa-plus text-white"></i>';
                    backgroundColorText = 'bg-warning';
                }
                return {
                    message: value.announcement,
                    urlId: value.entityId,
                    backgroundColor: backgroundColorText,
                    iconHTML: iconHTMLText,
                    timestamp: new Date(value.timestamp),
                    entityId: value.entityId
                };
            });
        }
    }
}
