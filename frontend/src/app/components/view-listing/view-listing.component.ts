import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {Listing} from '../../model/Listing';
import {ListingsService} from '../../services/listings.service';
import {flatMap} from 'rxjs/operators';
import {DealsService} from '../../services/deals.service';
import {Location} from '@angular/common';
import {City} from '../../model/City';
import {UserService} from '../../services/user.service';
import {PrivateUser} from '../../model/users/PrivateUser';
import {Offer} from '../../model/Offer';
import {BsModalRef, BsModalService} from 'ngx-bootstrap';
import {OffersService} from '../../services/offers.service';
import {Deal, DealDto} from '../../model/Deal';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-view-listing',
    templateUrl: './view-listing.component.html',
    styleUrls: ['./view-listing.component.css']
})
export class ViewListingComponent implements OnInit {

    listing: Listing = undefined;
    deal: Deal = undefined;
    loggedInUser: PrivateUser = undefined;
    offerAccept: Offer = undefined;
    offerToRemove: Offer = undefined;
    city = City;

    modalRef: BsModalRef;
    @ViewChild('acceptOfferModal') modal: TemplateRef<any>;
    @ViewChild('removeOfferModal') removeOfferModal: TemplateRef<any>;

    constructor(
        private service: ListingsService,
        private userService: UserService,
        private dealsService: DealsService,
        private location: Location,
        private route: ActivatedRoute,
        private offersService: OffersService,
        private modalService: BsModalService,
        private notifierService: NotifierService
    ) {
    }

    ngOnInit(): void {

        this.route.paramMap.pipe(
            flatMap( map => {
                const id = +map.get('id');
                return this.service.getOglasById(id);
            })
        ).subscribe(
            elem => {
                this.listing = elem;
                this.dealsService.getDealByOglasId(this.listing.id).subscribe(deal => {
                    this.deal = deal;
                }, error => this.deal = undefined);
            }
        );

        this.userService.getPrivateUser().subscribe(elem => this.loggedInUser = elem);
    }

    goBack() {
        this.location.back();
    }

    onOfferAccept(offer: Offer) {
        this.offerAccept = offer;
        this.openModal(this.modal);
    }

    openModal(template: TemplateRef<any>) {
        this.modalRef = this.modalService.show(template);
    }

    onModalDecline() {
        this.modalRef.hide();
    }

    onOfferAcceptModalAccept() {
        this.modalRef.hide();
        this.acceptOffer();
    }

    onOfferRemove($event: Offer) {
        this.offerToRemove = $event;
        this.openModal(this.removeOfferModal);
    }

    acceptOffer() {
        const deal: DealDto = {
            listingId: this.listing.id,
            userId: this.listing.userId.id,
            madeById: this.offerAccept.madeBy.id,
            price: this.offerAccept.offeredPrice
        };

        this.dealsService.createNewDeal(deal).subscribe(
            (response) => {
                window.location.reload();
            }
        );
    }

    onOfferRemoveModalAccept() {
        this.offersService.removeOfferById(this.offerToRemove.id).subscribe(
            success => {
                window.location.reload();
                this.notifierService.notify('success', 'Огласот избришан.');
            }, error => this.notifierService.notify('error', 'Се случи грешка! Обидете се повторно...')
        );
    }
}
