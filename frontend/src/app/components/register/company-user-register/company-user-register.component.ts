import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {UserService} from '../../../services/user.service';
import {City} from '../../../model/City';
import {formatDate} from '@angular/common';
import {MustMatch} from '../../../validators/must-match.validator';
import {Router} from '@angular/router';
import {UserStateService} from '../../../services/user-state.service';
import {PrivateUser} from '../../../model/users/PrivateUser';

@Component({
  selector: 'app-company-user-register',
  templateUrl: './company-user-register.component.html',
  styleUrls: ['./company-user-register.component.css']
})
export class CompanyUserRegisterComponent implements OnInit {

    public wasRegistrationSuccessful = false;
    public doesEmailExist: boolean;
    public wasSubmitted = false;
    public cities = City;

    registerForm = this.formBuilder.group({
        name: ['', Validators.required],
        emailAddress: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', [Validators.required]],
        city: ['', Validators.required],
        phoneNumber: ['', Validators.required],
        address: ['', Validators.required],
        zipCode: ['', Validators.required],
        description: ['', Validators.required]
    }, {
        validator: MustMatch('password', 'confirmPassword')
    });

    constructor(
        private formBuilder: FormBuilder,
        private service: UserService,
        private router: Router,
        private userStateService: UserStateService
    ) { }

    ngOnInit(): void {
    }

    get f() {
        return this.registerForm.controls;
    }

    onSubmit() {
        this.wasSubmitted = true;

        if (!this.registerForm.valid) {
            return;
        }

        const userAccount = this.registerForm.value;
        userAccount.id = 0;

        console.log(userAccount);

        this.service.checkIfEmailExists(userAccount.emailAddress)
            .subscribe(elem => {

                if (elem === false) {
                    this.wasRegistrationSuccessful = true;
                    this.service.addPrivateUser(userAccount).subscribe(
                        (user: PrivateUser) => {
                            sessionStorage.setItem('key', user.id.toString());
                            localStorage.setItem('account', JSON.stringify(user));
                            setTimeout(() => this.router.navigate(['/']), 1000);
                        }
                    );
                } else {
                    this.doesEmailExist = true;
                }
            });
    }
}
