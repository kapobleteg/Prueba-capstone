import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AboutMePageComponent } from './pages/about-me-page/about-me-page.component';
import { ContactPageComponent } from './pages/contact-page/contact-page.component';
import { BoxRentalPageComponent } from './pages/box-rental-page/box-rental-page.component'; 
import { ReservationPageComponent } from './pages/reservation-page/reservation-page.component';
import { AdminPageComponent } from './pages/admin-page/admin-page.component';
import { ConfirmationPageComponent } from './pages/confirmation-page/confirmation-page.component';
import { BoxConfirmationPageComponent } from './pages/box-confirmation-page/box-confirmation-page.component';
import { SuccessfulPaymentPageComponent } from './pages/successful-payment-page/successful-payment-page.component';
import { BoxSuccessfulPaymentPageComponent } from './pages/box-successful-payment-page/box-successful-payment-page.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PasswordResetInitComponent } from './pages/password-reset-init/password-reset-init.component';
import { RegistrationPasswordComponent } from './pages/registration-password/registration-password.component';
import { AuthGuard } from './authentication/auth/guard/auth.guard';


const routes: Routes = [
  { path: 'home', component: HomePageComponent },
  { path: 'confirmar-box/:id', component: BoxConfirmationPageComponent },
  { path: 'box-successful-payment', component: BoxSuccessfulPaymentPageComponent },  
  { path: 'successful-payment', component: SuccessfulPaymentPageComponent },
  { path: 'about-me', component: AboutMePageComponent },
  { path: 'contact', component: ContactPageComponent },
  { path: 'box-rental', component: BoxRentalPageComponent } ,
  { path: 'confirmation-page', component: ConfirmationPageComponent },
  { path: 'reservation', component: ReservationPageComponent },
  { path: 'admin', component: AdminPageComponent },
  { path: 'password-reset-init', component: PasswordResetInitComponent },
  { path: 'reset/finish', component: RegistrationPasswordComponent },
  { path: 'login', component: LoginComponent },
  { path: 'dashboard', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: '', redirectTo: '/home', pathMatch: 'full' },
  { path: '**', redirectTo: '/home' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
