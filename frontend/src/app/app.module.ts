
// @Angular Components
import {AppComponent} from './app.component';
import {RegisterComponent} from './components/register/register.component';
import {FormsModule} from '@angular/forms';

// @Angular Modules
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';
import {AppRoutingModule} from './app-routing.module';
import {ReactiveFormsModule} from '@angular/forms';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

// Application Components
import { TopNavigationComponent } from './components/top-navigation/top-navigation.component';
import { SideNavigationComponent } from './components/side-navigation/side-navigation.component';
import { HomeComponent } from './components/home/home.component';

// Miscellaneous
import { OnlyNumberDirective } from './directives/only-number.directive';
import { EnumToArrayPipe } from './pipes/enum-to-array.pipe';

// External Components
import {BsDatepickerModule} from 'ngx-bootstrap/datepicker';
import {PaginationModule} from 'ngx-bootstrap/pagination';
import {ModalModule} from 'ngx-bootstrap/modal';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { NotifierModule } from 'angular-notifier';

import { PrivateUserRegisterComponent } from './components/register/private-user-register/private-user-register.component';
import { CompanyUserRegisterComponent } from './components/register/company-user-register/company-user-register.component';
import { LoginComponent } from './components/login/login.component';
import { ListingsTableComponent } from './components/listings-table/listings-table.component';
import { ListingsTableItemComponent } from './components/listings-table/listings-table-item/listings-table-item.component';
import { DealsTableComponent } from './components/deals-table/deals-table.component';
import { AllListingsTableComponent } from './components/all-listings-table/all-listings-table.component';
import { ViewListingComponent } from './components/view-listing/view-listing.component';
import { ViewListingOffersTableComponent } from './components/view-listing/view-listing-offers-table/view-listing-offers-table.component';
import {BasicAuthHttpInterceptor} from './interceptors/basic-auth-http.interceptor';
import { ProfileSettingsComponent } from './components/profile-settings/profile-settings.component';
import { NewListingComponent } from './components/new-listing/new-listing.component';
import { SafeHtmlPipe } from './pipes/safe-html.pipe';
import { DateToStringPipe } from './pipes/date-to-string.pipe';
import { AllOffersTableComponent } from './components/all-offers-table/all-offers-table.component';
import { AllDealsTableComponent } from './components/all-deals-table/all-deals-table.component';
import { ProfileComponent } from './components/profile/profile.component';
import { AllGlobalAnnouncementsComponent } from './components/all-global-announcements/all-global-announcements.component';
import { ChangePasswordComponent } from './components/profile-settings/change-password/change-password.component';

@NgModule({
    declarations: [
        AppComponent,
        RegisterComponent,
        EnumToArrayPipe,
        TopNavigationComponent,
        SideNavigationComponent,
        HomeComponent,
        OnlyNumberDirective,
        PrivateUserRegisterComponent,
        CompanyUserRegisterComponent,
        LoginComponent,
        ListingsTableComponent,
        ListingsTableItemComponent,
        DealsTableComponent,
        AllListingsTableComponent,
        ViewListingComponent,
        ViewListingOffersTableComponent,
        ProfileSettingsComponent,
        NewListingComponent,
        SafeHtmlPipe,
        DateToStringPipe,
        AllOffersTableComponent,
        AllDealsTableComponent,
        ProfileComponent,
        AllGlobalAnnouncementsComponent,
        ChangePasswordComponent,
    ],
    imports: [
        BrowserAnimationsModule,
        BrowserModule,
        AppRoutingModule,
        FormsModule,
        ReactiveFormsModule,
        HttpClientModule,
        BsDatepickerModule.forRoot(),
        PaginationModule.forRoot(),
        ModalModule.forRoot(),
        CollapseModule.forRoot(),
        NotifierModule.withConfig({
            position: {
                horizontal: {
                    position: 'right'
                }
            }
        })
    ],
    providers: [
        { provide: HTTP_INTERCEPTORS, useClass: BasicAuthHttpInterceptor, multi: true }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {
}
