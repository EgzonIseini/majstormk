import { Component, OnInit } from '@angular/core';
import {FormControl} from '@angular/forms';
import {BehaviorSubject, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {DealsService} from '../../services/deals.service';
import {PagedDeal} from '../../model/PagedDeal';

@Component({
  selector: 'app-deals-table',
  templateUrl: './deals-table.component.html',
  styleUrls: ['./deals-table.component.css']
})
export class DealsTableComponent implements OnInit {

    searchDeal = new FormControl('');

    pagedDeal: PagedDeal;
    pageNumber: number;
    searchQuery: string;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);

    getData$: Subject<boolean> = new Subject();

    constructor(
        private dealsService: DealsService
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                return this.dealsService.getAllDealsByUser(
                    this.pageNumber - 1,
                    this.pagesToShow$.getValue(),
                    this.searchQuery);
            })
        ).subscribe(
            pagedDeal => {
                this.pagedDeal = pagedDeal;
            }
        );

        this.pagesToShow$.subscribe(
            () => this.getData$.next(true)
        );

        this.searchDeal.valueChanges.pipe(
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
}
