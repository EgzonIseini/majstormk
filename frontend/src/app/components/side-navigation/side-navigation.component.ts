import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from '../../services/authentication.service';

@Component({
    selector: 'app-side-navigation',
    templateUrl: './side-navigation.component.html',
    styleUrls: ['./side-navigation.component.css']
})
export class SideNavigationComponent implements OnInit {


    isToggled = true;

    constructor(public authenticationService: AuthenticationService) {
    }

    ngOnInit(): void {
    }

    onSideNavToggle() {
        this.isToggled = !this.isToggled;
    }

}
