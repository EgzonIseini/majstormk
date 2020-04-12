import {Component, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {FormBuilder, Validators} from '@angular/forms';
import {MustMatch} from '../../../validators/must-match.validator';
import {AuthenticationService} from '../../../services/authentication.service';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-change-password',
    templateUrl: './change-password.component.html',
    styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

    changePassword = this.formBuilder.group({
        oldPassword: ['', Validators.required],
        newPassword: ['', [Validators.required, Validators.minLength(8)]],
        newPasswordConfirm: ['', [Validators.required]],
    }, {
        validator: MustMatch('newPassword', 'newPasswordConfirm')
    });

    oldPasswordIncorrect = false;

    constructor(
        private location: Location,
        private formBuilder: FormBuilder,
        private authenticationService: AuthenticationService,
        private notifierService: NotifierService
    ) {
    }

    ngOnInit(): void {
    }

    get f() {
        return this.changePassword.controls;
    }

    onSubmit() {
        if (!this.changePassword.valid) {
            this.notifierService.notify('error', 'Мора да ги пополните сите полиња!');
            return;
        }

        const password = this.changePassword.value.oldPassword;
        const newPassword = this.changePassword.value.newPassword;
        this.authenticationService.checkPassword(password).subscribe(
            success => {
                this.notifierService.notify('success', 'Успешно ја променивте лозинката.');
                this.authenticationService.changePassword(newPassword).subscribe(
                    // tslint:disable-next-line:no-shadowed-variable
                    success => window.location.href = '/profile',
                    error => {
                        this.notifierService.notify('error', 'Се случи некоја грешка... Обидете се повторно.');
                    }
                )
            },
            error => this.oldPasswordIncorrect = true
        );
    }

    goBack() {
        this.location.back();
    }
}
