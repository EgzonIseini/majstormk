import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {City} from '../../model/City';
import {BehaviorSubject, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {OffersService} from '../../services/offers.service';
import {PagedOffer} from '../../model/PagedOffer';

@Component({
    selector: 'app-all-offers-table',
    templateUrl: './all-offers-table.component.html',
    styleUrls: ['./all-offers-table.component.css']
})
export class AllOffersTableComponent implements OnInit {

    searchOglas = new FormControl('');

    pagedOffer: PagedOffer;
    pageNumber: number;
    searchQuery: string;
    city = City;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);
    sortedBy$: BehaviorSubject<string> = new BehaviorSubject<string>('madeBy');

    getData$: Subject<boolean> = new Subject();

    constructor(
        private offersService: OffersService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                // tslint:disable-next-line:max-line-length
                return this.offersService.getAllOffers(this.pageNumber - 1, this.pagesToShow$.getValue(), this.searchQuery, this.sortedBy$.getValue());
            })
        ).subscribe(
            pagedOffer => {
                this.pagedOffer = pagedOffer;
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
