import {Component, OnInit} from '@angular/core';
import {City} from '../../model/City';
import {Location} from '@angular/common';
import {FormBuilder, Validators} from '@angular/forms';
import {ListingsService} from '../../services/listings.service';
import {Router} from '@angular/router';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-new-listing',
    templateUrl: './new-listing.component.html',
    styleUrls: ['./new-listing.component.css']
})
export class NewListingComponent implements OnInit {

    today = new Date();
    cities = City;
    newOglasForm = this.formBuilder.group({
        name: ['', Validators.required],
        price: ['', Validators.required],
        dueDate: [this.today, Validators.required],
        city: ['Skopje', Validators.required],
        description: ['', Validators.required]
    });

    constructor(
        private location: Location,
        private formBuilder: FormBuilder,
        private listingsService: ListingsService,
        private notifierService: NotifierService,
        private router: Router
    ) {
    }

    ngOnInit(): void {
    }

    goBack() {
        this.location.back();
    }

    onSubmit() {

        if (!this.newOglasForm.valid) {
            this.notifierService.notify('error', 'Мора да ги внесете сите информации!');
            return;
        }

        this.notifierService.notify('info', 'Го поставувам огласот, ве молиме почекајте...');
        const listing = this.newOglasForm.value;
        listing.isActive = true;
        this.listingsService.createOglas(listing).subscribe(
            () => {
                this.notifierService.notify('success', 'Огласот е објавен!');
                this.router.navigate(['']);
            },
            () => this.notifierService.notify('error', 'Се случи грешка! Обидете се повторно.')
        );
    }
}
