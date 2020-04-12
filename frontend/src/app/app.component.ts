import {Component, OnInit} from '@angular/core';
import {PrivateUser} from './model/users/PrivateUser';
import {UserService} from './services/user.service';
import {UserStateService} from './services/user-state.service';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent {

    title = 'majstormk';

}
