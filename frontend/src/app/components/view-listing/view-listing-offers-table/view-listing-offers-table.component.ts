import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormControl} from '@angular/forms';
import {BehaviorSubject, of, Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged, switchMap} from 'rxjs/operators';
import {OffersService} from '../../../services/offers.service';
import {PagedOffer} from '../../../model/PagedOffer';
import {ActivatedRoute} from '@angular/router';
import {Listing} from '../../../model/Listing';
import {PrivateUser} from '../../../model/users/PrivateUser';
import {Offer} from '../../../model/Offer';
import {Deal} from '../../../model/Deal';

@Component({
    selector: 'app-view-listing-offers-table',
    templateUrl: './view-listing-offers-table.component.html',
    styleUrls: ['./view-listing-offers-table.component.css']
})
export class ViewListingOffersTableComponent implements OnInit {

    @Input() oglas: Listing;
    @Input() loggedInUser: PrivateUser;

    @Output() onOfferAcceptEmit = new EventEmitter<Offer>();
    @Output() removeOffer = new EventEmitter<Offer>();

    searchOglas = new FormControl('');
    offerDetails = new FormControl('');
    price = new FormControl('');

    pagedOffers: PagedOffer;
    pageNumber: number;
    searchQuery: string;
    activeTab = 'all';
    newOfferToggled = false;

    pagesToShow$: BehaviorSubject<number> = new BehaviorSubject(10);

    getData$: Subject<boolean> = new Subject();
    isCollapsed = false;

    constructor(
        private service: OffersService,
        private route: ActivatedRoute
    ) {
        this.pageNumber = 1;
    }

    ngOnInit(): void {
        this.getData$.pipe(
            switchMap(() => {
                if (this.activeTab === 'all') {
                    return this.service.getOffersByOglasId(this.route.snapshot.params.id);
                } else {
                    return this.service.getPersonalOffersByOglasId(this.route.snapshot.params.id);
                }
            })
        ).subscribe(
            pagedOffer => {
                this.pagedOffers = pagedOffer;
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

        this.price.setValue(this.oglas.price);
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
        if (this.activeTab === event) {
            return;
        }

        this.activeTab = event;
        this.getData$.next();
    }

    onSubmitOffer() {
        const newOffer = {
            userId: 1,
            description: this.offerDetails.value,
            wasAccepted: false,
            oglasId: this.oglas.id,
            offeredPrice: this.price.value
        };
        this.service.createNewOffer(newOffer).subscribe(
            () => {
                this.offerDetails.setValue('');
                this.newOfferToggled = false;
                this.getData$.next(true);
            });
    }

    onOfferAccept(offer: Offer) {
        this.onOfferAcceptEmit.emit(offer);
    }

    onRemoveOffer(offer: Offer) {
        this.removeOffer.emit(offer);
    }
}
