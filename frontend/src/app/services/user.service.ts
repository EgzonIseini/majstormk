import {Injectable} from '@angular/core';
import {PrivateUser} from '../model/users/PrivateUser';
import {Observable} from 'rxjs';
import {HttpClient} from '@angular/common/http';
import {CompanyUser} from '../model/users/CompanyUser';

@Injectable({
    providedIn: 'root'
})
export class UserService {

    constructor(
        private http: HttpClient
    ) { }

    public addPrivateUser(user: PrivateUser): Observable<PrivateUser | undefined> {
        return this.http.post<PrivateUser | undefined>('/api/users/auth/register/private', user, {
            responseType: 'json'
        });
    }

    public addCompanyUser(user: CompanyUser): Observable<CompanyUser | undefined> {
        return this.http.post<CompanyUser | undefined>('/api/users/auth/register/company', user, {
            responseType: 'json'
        });
    }

    public checkIfEmailExists(email: string): Observable<boolean> {
        return this.http.get<boolean>('/api/users/email?q=' + email);
    }

    public getPrivateUser(): Observable<PrivateUser> {
        return this.http.get<PrivateUser>('/api/users');
    }

    public modifyPrivateUser(user: PrivateUser): Observable<PrivateUser> {
        return this.http.put<PrivateUser>('/api/users/private', user, {
            responseType: 'json'
        });
    }
}
