import {Injectable} from '@angular/core';
import {UserService} from './user.service';
import {HttpClient} from '@angular/common/http';
import {map} from 'rxjs/operators';
import {UserStateService} from './user-state.service';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class AuthenticationService {

    constructor(
        private userService: UserService,
        private userStateService: UserStateService,
        private http: HttpClient,
    ) {
    }

    authenticate(username: string, password: string) {
        return this.http.post<any>('/api/users/auth/login', {username, password}).pipe(
            map(
                userData => {
                    const tokenStr = userData.tokenType + ' ' + userData.accessToken;
                    sessionStorage.setItem('token', tokenStr);
                    return userData;
                }
            )
        );
    }

    checkPassword(password: string): Observable<any> {
        return this.http.post('/api/users/auth/check-password', password);
    }

    changePassword(password: string): Observable<any> {
        return this.http.post('/api/users/auth/change-password', password);
    }

    isUserLoggedIn(): boolean {
        const token = sessionStorage.getItem('token');
        return !(token === null);
    }

    logOut() {
        document.location.href = '/';
        sessionStorage.removeItem('token');
        this.userStateService.account = undefined;
    }

}
