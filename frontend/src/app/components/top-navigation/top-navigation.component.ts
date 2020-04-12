import {Component, Input, OnInit,} from '@angular/core';
import {PrivateUser} from '../../model/users/PrivateUser';
import {AuthenticationService} from '../../services/authentication.service';
import {AnnouncementService} from '../../services/announcement.service';
import {TransformedAnnouncement} from '../../model/Announcement';
import {UserService} from '../../services/user.service';

@Component({
    selector: 'app-top-navigation',
    templateUrl: './top-navigation.component.html',
    styleUrls: ['./top-navigation.component.css']
})
export class TopNavigationComponent implements OnInit {

    account: PrivateUser;

    announcements: TransformedAnnouncement[];

    constructor(
        private userService: UserService,
        private announcementService: AnnouncementService,
        public authenticationService: AuthenticationService) {
    }

    ngOnInit(): void {

        this.userService.getPrivateUser().subscribe(
            success => this.account = success,
            error => console.error(error)
        );

        if (this.authenticationService.isUserLoggedIn()) {
            this.announcementService.getLatest5GlobalAnnouncements().subscribe(
                elem => this.announcements = elem
            );
        }

    }

}
