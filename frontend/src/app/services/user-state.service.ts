import {Injectable} from '@angular/core';
import {PrivateUser} from '../model/users/PrivateUser';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class UserStateService {

    private account$: BehaviorSubject<PrivateUser> = new BehaviorSubject<PrivateUser>(undefined);

    get account() {
        console.log('CURRENT USER IS ', this.account$.value);
        return this.account$.value;
    }

    set account(user: PrivateUser) {
        console.log('NEXT USER IS ', user);
        this.account$.next(user);
    }
}
