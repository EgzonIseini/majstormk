import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { PagedListing } from '../model/PagedListing';
import {Listing} from '../model/Listing';

@Injectable({
    providedIn: 'root'
})
export class ListingsService {

    constructor(
        private http: HttpClient
    ) { }

    public getAllOglasiByUserId(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = ''): Observable<PagedListing> {
        return this.http.get<PagedListing>(`/api/oglasi/user?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}`);
    }

    public getAllActiveOglasiByUserId(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = ''): Observable<PagedListing> {
        return this.http.get<PagedListing>(`/api/oglasi/user/active?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}`);
    }

    public getAllUnsuccessfulOglasiByUserId(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = ''): Observable<PagedListing> {
        return this.http.get<PagedListing>(`/api/oglasi/user/unsuccessful?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}`);
    }

    public getAllEndedOglasiByUserId(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = ''): Observable<PagedListing> {
        return this.http.get<PagedListing>(`/api/oglasi/user/ended?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}`);
    }

    public getAllOglasi(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = '',
        sortBy: string = ''): Observable<PagedListing> {
        return this.http.get<PagedListing>(`/api/oglasi/?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}&sortBy=${sortBy}`);
    }

    public getOglasById(listingId: number) {
        return this.http.get<Listing>(`/api/oglasi/${listingId}`);
    }

    public createOglas(listing: Listing): Observable<Listing> {
        return this.http.post<Listing>('/api/oglasi', listing, {
            responseType: 'json'
        });
    }

    public deleteOglasById(listingId: number) {
        return this.http.delete('/api/oglasi/' + listingId);
    }
}
