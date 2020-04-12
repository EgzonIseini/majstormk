import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Listing} from '../model/Listing';
import {Observable, of} from 'rxjs';
import {PagedOffer} from '../model/PagedOffer';
import {Offer} from '../model/Offer';
import {PagedListing} from '../model/PagedListing';

@Injectable({
  providedIn: 'root'
})
export class OffersService {

  constructor(private http: HttpClient) { }

    public getOffersByOglasId(listingId: number): Observable<PagedOffer> {
        return this.http.get<PagedOffer>(`/api/offers/oglas/${listingId}`);
    }

    public getPersonalOffersByOglasId(listingId: number): Observable<PagedOffer> {
        return this.http.get<PagedOffer>(`/api/offers/oglas/${listingId}/personal`);
    }

    public createNewOffer(offer: any): Observable<Offer> {
      return this.http.post<Offer>('/api/offers', offer, { responseType: 'json'});
    }

    public getAllOffers(
        pageNo: number = 0,
        pageSize: number = 10,
        searchQuery: string = '',
        sortBy: string = ''): Observable<PagedOffer> {
        return this.http.get<PagedOffer>(`/api/offers?pageNo=${pageNo}&pageSize=${pageSize}&search=${searchQuery}&sortBy=${sortBy}`);
    }

    public removeOfferById(offerId: number) {
      return this.http.delete('/api/offers/delete/' + offerId);
    }

}
