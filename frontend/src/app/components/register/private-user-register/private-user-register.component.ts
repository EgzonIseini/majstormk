import {Component, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {UserService} from '../../../services/user.service';
import {City} from '../../../model/City';
import {formatDate} from '@angular/common';
import {MustMatch} from '../../../validators/must-match.validator';
import {Router} from '@angular/router';
import {UserStateService} from '../../../services/user-state.service';
import {AuthenticationService} from '../../../services/authentication.service';
import {PrivateUser} from '../../../model/users/PrivateUser';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-private-user-register',
    templateUrl: './private-user-register.component.html',
    styleUrls: ['./private-user-register.component.css']
})
export class PrivateUserRegisterComponent implements OnInit {

    public wasRegistrationSuccessful = false;
    public doesEmailExist: boolean;
    public wasSubmitted = false;
    public cities = City;

    public errorMessage: string = undefined;

    registerForm = this.formBuilder.group({
        firstName: ['', Validators.required],
        lastName: ['', Validators.required],
        emailAddress: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required]],
        dateOfBirth: [new Date(), Validators.required],
        city: ['', Validators.required],
        phoneNumber: ['', Validators.required],
        address: [''],
        zipCode: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confirmPassword')
    });

    constructor(
        private formBuilder: FormBuilder,
        private service: UserService,
        private router: Router,
        private userStateService: UserStateService,
        private authenticationService: AuthenticationService,
        private notifierService: NotifierService
    ) {
    }

    ngOnInit(): void {
    }

    get f() {
        return this.registerForm.controls;
    }


    onSubmit() {
        this.wasSubmitted = true;

        if (!this.registerForm.valid) {
            this.notifierService.notify('error', 'Внесовте невалидни информации при регистрација.');
            return;
        }

        const userAccount = this.registerForm.value;
        userAccount.dateOfBirth = formatDate(this.registerForm.value.dateOfBirth, 'dd/MM/yyyy', 'en');
        userAccount.id = 0;
        userAccount.roles = [{placeholder: 'placeholder'}];

        this.notifierService.notify('info', 'Се регистирам. Ве молиме почекајте...');
        this.service.addPrivateUser(userAccount).subscribe(
            (user: PrivateUser) => {
                this.notifierService.notify('success', 'Регистрацијата беше успешна! Се логирам...');
                this.authenticationService.authenticate(userAccount.emailAddress, userAccount.password).subscribe(
                    () => window.location.href = '',
                    // () => this.notifierService.notify('warning', 'Се случи грешка...')
                    error => console.log('Error was: ', error)
                );
            },
            error => this.errorMessage = error.error?.message
        );

    }
}
