import {Component, OnInit} from '@angular/core';
import {City} from '../../model/City';
import {UserService} from '../../services/user.service';
import {PrivateUser} from '../../model/users/PrivateUser';
import {FormBuilder, Validators} from '@angular/forms';
import {MustMatch} from '../../validators/must-match.validator';
import {formatDate} from '@angular/common';

@Component({
    selector: 'app-profile-settings',
    templateUrl: './profile-settings.component.html',
    styleUrls: ['./profile-settings.component.css']
})
export class ProfileSettingsComponent implements OnInit {

    cities = City;
    user: PrivateUser = undefined;

    modifiedSuccess: boolean = undefined;
    modifiedFail: boolean = undefined;

    modifyForm = this.formBuilder.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        emailAddress: [{value: '', disabled: true}, [Validators.required, Validators.email]],
        dateOfBirth: [new Date(), Validators.required],
        city: ['', Validators.required],
        phoneNumber: ['', Validators.required],
        address: [''],
        zipCode: ['', Validators.required]
    });

    constructor(
        private userService: UserService,
        private formBuilder: FormBuilder
    ) {
    }

    ngOnInit(): void {
        this.userService.getPrivateUser().subscribe(user => {
            user.dateOfBirth = new Date(user.dateOfBirth);
            this.user = user;
            this.modifyForm.patchValue(user);
        });
    }

    onSubmit() {
        const userAccount = {...this.user, ...this.modifyForm.value};
        userAccount.dateOfBirth = formatDate(userAccount.dateOfBirth, 'dd/MM/yyyy', 'en', '+0100');
        this.userService.modifyPrivateUser(userAccount).subscribe(
            success => this.modifiedSuccess = true,
            fail => this.modifiedFail = true
        );
    }

}
