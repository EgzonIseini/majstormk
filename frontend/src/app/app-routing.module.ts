import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HomeComponent} from './components/home/home.component';
import {RegisterComponent} from './components/register/register.component';
import {PrivateUserRegisterComponent} from './components/register/private-user-register/private-user-register.component';
import {CompanyUserRegisterComponent} from './components/register/company-user-register/company-user-register.component';
import {LoginComponent} from './components/login/login.component';
import {AllListingsTableComponent} from './components/all-listings-table/all-listings-table.component';
import {ViewListingComponent} from './components/view-listing/view-listing.component';
import {AuthGuardService} from './services/auth-guard.service';
import {ProfileSettingsComponent} from './components/profile-settings/profile-settings.component';
import {NewListingComponent} from './components/new-listing/new-listing.component';
import {AllOffersTableComponent} from './components/all-offers-table/all-offers-table.component';
import {AllDealsTableComponent} from './components/all-deals-table/all-deals-table.component';
import {ProfileComponent} from './components/profile/profile.component';
import {AllGlobalAnnouncementsComponent} from './components/all-global-announcements/all-global-announcements.component';
import {ChangePasswordComponent} from './components/profile-settings/change-password/change-password.component';


const routes: Routes = [
    {path: '', component: HomeComponent, canActivate: [AuthGuardService]},
    {
        path: 'register', component: RegisterComponent, children: [
            {path: 'private', component: PrivateUserRegisterComponent},
            {path: 'company', component: CompanyUserRegisterComponent},
            {path: '', redirectTo: 'private', pathMatch: 'full'}
        ]
    },
    {path: 'oglasi', component: AllListingsTableComponent, canActivate: [AuthGuardService]},
    {path: 'oglasi/new', component: NewListingComponent, canActivate: [AuthGuardService]},
    {path: 'oglasi/:id', component: ViewListingComponent, canActivate: [AuthGuardService]},
    {path: 'login', component: LoginComponent},
    {path: 'settings', component: ProfileSettingsComponent},
    {path: 'settings/password', component: ChangePasswordComponent},
    {path: 'profile', component: ProfileComponent},
    {path: 'offers', component: AllOffersTableComponent, canActivate: [AuthGuardService]},
    {path: 'deals', component: AllDealsTableComponent, canActivate: [AuthGuardService]},
    {path: 'announcements/global', component: AllGlobalAnnouncementsComponent, canActivate: [AuthGuardService]},
    {path: '**', redirectTo: ''}
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
