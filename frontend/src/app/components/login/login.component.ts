import {Component, OnInit} from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';
import {FormBuilder, Validators} from '@angular/forms';
import {MustMatch} from '../../validators/must-match.validator';
import {formatDate} from '@angular/common';
import {PrivateUser} from '../../model/users/PrivateUser';
import {NotifierService} from 'angular-notifier';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

    invalidLogin = false;

    loginForm = this.formBuilder.group({
        username: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]]
    });

    constructor(
        private authenticationService: AuthenticationService,
        private formBuilder: FormBuilder,
        private notifierService: NotifierService
    ) { }

    ngOnInit(): void {
    }

    get f() {
        return this.loginForm.controls;
    }

    onSubmit() {

        if (!this.loginForm.valid) {
            this.notifierService.notify('error', 'Ве молиме внесете ги сите информации.');
            return;
        }

        const loginData = this.loginForm.value;

        this.notifierService.notify('info', 'Се логирам. Ве молиме почекајте...');
        this.authenticationService.authenticate(loginData.username, loginData.password).subscribe(
            success => window.location.href = '',
            error => this.notifierService.notify('error', 'Внесовте погрешна електронска адреса или лозинка.')
        );

    }
}
