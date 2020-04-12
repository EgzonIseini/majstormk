import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {PagedListing} from '../../model/PagedListing';
import {BehaviorSubject, Subject} from 'rxjs';
import {ListingsService} from '../../services/listings.service';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {City} from '../../model/City';

@Component({
    selector: 'app-all-listings-table',
    templateUrl: './all-listings-table.component.html',
    styleUrls: ['./all-listings-table.component.css']
})
export class AllListingsTableComponent implements OnInit {

    searchOglas = new FormControl('');

    pagedListing: PagedListing;
    pageNumber: number;
    searchQuery: string;
    city = City;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);
    sortedBy$: BehaviorSubject<string> = new BehaviorSubject<string>('name');

    getData$: Subject<boolean> = new Subject();

    constructor(
        private listingsService: ListingsService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                // tslint:disable-next-line:max-line-length
                return this.listingsService.getAllOglasi(this.pageNumber - 1, this.pagesToShow$.getValue(), this.searchQuery, this.sortedBy$.getValue());
            })
        ).subscribe(
            pagedList => {
                this.pagedListing = pagedList;
            }
        );

        this.pagesToShow$.subscribe(
            () => this.getData$.next(true)
        );

        this.sortedBy$.subscribe(
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

    onSortedByChange(event: any) {
        this.sortedBy$.next(event.target.value);
    }
}
