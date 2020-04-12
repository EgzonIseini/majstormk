import {Component, OnInit} from '@angular/core';
import {AnnouncementService} from '../../services/announcement.service';
import {PagedAnnouncements} from '../../model/PagedAnnouncements';
import {BehaviorSubject, Subject} from 'rxjs';
import {switchMap} from 'rxjs/operators';

@Component({
    selector: 'app-all-global-announcements',
    templateUrl: './all-global-announcements.component.html',
    styleUrls: ['./all-global-announcements.component.css']
})
export class AllGlobalAnnouncementsComponent implements OnInit {

    pagedAnnouncements: PagedAnnouncements = undefined;
    pageNumber: number;

    getData$: Subject<boolean> = new Subject();
    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);

    constructor(
        private announcementService: AnnouncementService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                return this.announcementService.getAllGlobalAnnouncements(this.pageNumber - 1, this.pagesToShow$.getValue());
            })
        ).subscribe(pagedAnnouncements => this.pagedAnnouncements = pagedAnnouncements);

        this.pagesToShow$.subscribe(
            () => this.getData$.next(true)
        );
    }

    onPageSelected(pageChangeEvent: any) {
        this.pageNumber = pageChangeEvent.page;
        this.getData$.next();
        document.getElementById('top').scrollIntoView();
    }

    onTotalElementsToShowChange(event: any) {
        // tslint:disable-next-line:no-non-null-assertion
        const elementsSize = +event.target.value!;
        this.pagesToShow$.next(elementsSize);
    }
}
