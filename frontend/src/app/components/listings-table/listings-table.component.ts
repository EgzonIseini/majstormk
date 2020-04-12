import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Subject, BehaviorSubject} from 'rxjs';
import { switchMap, distinctUntilChanged, debounceTime } from 'rxjs/operators';
import { ListingsService } from 'src/app/services/listings.service';
import { PagedListing } from 'src/app/model/PagedListing';
import { FormControl } from '@angular/forms';
import {City} from '../../model/City';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {Listing} from '../../model/Listing';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-listings-table',
    templateUrl: './listings-table.component.html',
    styleUrls: ['./listings-table.component.css']
})
export class ListingsTableComponent implements OnInit {

    searchOglas = new FormControl('');

    pagedListing: PagedListing;
    pageNumber: number;
    searchQuery: string;
    activeTab = 'active';
    city = City;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);

    getData$: Subject<boolean> = new Subject();

    modalRef: BsModalRef;
    @ViewChild('removeOglasModal') removeOglasModal: TemplateRef<any>;

    oglasToRemove: Listing = undefined;

    constructor(
        private listingsService: ListingsService,
        private notifierService: NotifierService,
        private modalService: BsModalService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                if (this.activeTab === 'active') {
                    return this.listingsService.getAllActiveOglasiByUserId(
                        this.pageNumber - 1, this.pagesToShow$.getValue(), this.searchQuery);
                } else if (this.activeTab === 'ended') {
                    return this.listingsService.getAllEndedOglasiByUserId(
                        this.pageNumber - 1, this.pagesToShow$.getValue(), this.searchQuery);
                } else {
                    return this.listingsService.getAllUnsuccessfulOglasiByUserId(
                        this.pageNumber - 1, this.pagesToShow$.getValue(), this.searchQuery);
                }
            })
        ).subscribe(
            pagedList => {
                this.pagedListing = pagedList;
            }
        );

        this.pagesToShow$.subscribe(
            () => this.getData$.next(true)
        );

        this.searchOglas.valueChanges.pipe(
            debounceTime(500),
            distinctUntilChanged(),
        ).subscribe(
            // tslint:disable-next-line:no-shadowed-variable
            (query) => {
                this.searchQuery = query;
                this.getData$.next(true);
            }
        );

    }

    onTotalElementsToShowChange(event: any) {
        // tslint:disable-next-line:no-non-null-assertion
        const elementsSize = +event.target.value!;
        this.pagesToShow$.next(elementsSize);
    }

    onPageSelected(pageChangeEvent: any) {
        this.pageNumber = pageChangeEvent.page;
        this.getData$.next();
        document.getElementById('top').scrollIntoView();
    }

    onListingStateChange(event: string) {
        if (this.activeTab === event) { return; }

        this.activeTab = event;
        this.getData$.next();
    }

    onOglasRemove(listing: Listing) {
        this.oglasToRemove = listing;
        this.modalRef = this.modalService.show(this.removeOglasModal);
    }

    onOglasRemoveModalAccept() {
        this.listingsService.deleteOglasById(this.oglasToRemove.id).subscribe(
            succes => {
                window.location.reload();
                this.notifierService.notify('success', 'Огласот е избришан...');
            },
            error => this.notifierService.notify('error', 'Се случи грешка! Обидете се повторно...')
        );
    }
}
