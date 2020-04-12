import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PagedDeal} from '../model/PagedDeal';
import {Deal, DealDto} from '../model/Deal';
import {tap} from 'rxjs/operators';
import {PagedOffer} from '../model/PagedOffer';

@Injectable({
    providedIn: 'root'
})
export class DealsService {

    constructor(
        private http: HttpClient
    ) {
    }

    // tslint:disable-next-line:max-line-length
    public getAllDealsByUser(pageNo: number = 0, pageSize: number = 10, searchQuery: string = ''): Observable<PagedDeal> {
        return this.http.get<PagedDeal>(`/api/deals/user?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}`);
    }

    public getDealByOglasId(oglasId: number): Observable<Deal> {
        return this.http.get<Deal>(`/api/deals/oglas/${oglasId}`);
    }

    public createNewDeal(deal: DealDto): Observable<Deal> {
        return this.http.post<Deal>('/api/deals', deal);
    }

    public getAllDeals(
        pageNo: number = 0,
        pageSize: number = 10,
        sortBy: string = ''): Observable<PagedDeal> {
        return this.http.get<PagedDeal>(`/api/deals?pageNo=${pageNo}&pageSize=${pageSize}&sortBy=${sortBy}`);
    }

}
