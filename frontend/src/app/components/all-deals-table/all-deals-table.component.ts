import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {PagedOffer} from '../../model/PagedOffer';
import {City} from '../../model/City';
import {BehaviorSubject, Subject} from 'rxjs';
import {OffersService} from '../../services/offers.service';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {PagedDeal} from '../../model/PagedDeal';
import {DealsService} from '../../services/deals.service';

@Component({
    selector: 'app-all-deals-table',
    templateUrl: './all-deals-table.component.html',
    styleUrls: ['./all-deals-table.component.css']
})
export class AllDealsTableComponent implements OnInit {

    searchOglas = new FormControl('');

    pagedDeal: PagedDeal;
    pageNumber: number;
    searchQuery: string;
    city = City;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);
    sortedBy$: BehaviorSubject<string> = new BehaviorSubject<string>('user');
    getData$: Subject<boolean> = new Subject();

    constructor(
        private dealsService: DealsService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                // tslint:disable-next-line:max-line-length
                return this.dealsService.getAllDeals(this.pageNumber - 1, this.pagesToShow$.getValue(), this.sortedBy$.getValue());
            })
        ).subscribe(
            pagedDeal => {
                this.pagedDeal = pagedDeal;
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
