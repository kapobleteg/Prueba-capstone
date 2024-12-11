import { NgModule } from '@angular/core';
import { HTTP_INTERCEPTORS, HttpClientModule ,provideHttpClient} from '@angular/common/http';
import { withFetch } from '@angular/common/http';
import { CalendarModule } from 'primeng/calendar';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { NavbarPageComponent } from './pages/navbar-page/navbar-page.component';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { AboutMePageComponent } from './pages/about-me-page/about-me-page.component';
import { ContactPageComponent } from './pages/contact-page/contact-page.component';
import { FormsModule } from '@angular/forms';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { BoxRentalPageComponent } from './pages/box-rental-page/box-rental-page.component';
import { ReservationPageComponent } from './pages/reservation-page/reservation-page.component';
import { AdminPageComponent } from './pages/admin-page/admin-page.component';
import { ConfirmationPageComponent } from './pages/confirmation-page/confirmation-page.component';
import { BoxConfirmationPageComponent } from './pages/box-confirmation-page/box-confirmation-page.component';
import { AccordionModule } from 'primeng/accordion';
import { PanelModule } from 'primeng/panel';
import { DialogModule } from 'primeng/dialog'; 
import { ReactiveFormsModule } from '@angular/forms';
import { MenubarModule } from 'primeng/menubar';
import { SuccessfulPaymentPageComponent } from './pages/successful-payment-page/successful-payment-page.component';
import { BoxSuccessfulPaymentPageComponent } from './pages/box-successful-payment-page/box-successful-payment-page.component';
import { LoginComponent } from './pages/login/login.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PasswordResetInitComponent } from './pages/password-reset-init/password-reset-init.component';
import { CheckboxModule } from 'primeng/checkbox';
import { RippleModule } from 'primeng/ripple';
import { FileUploadModule } from 'primeng/fileupload';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { CarouselModule } from 'primeng/carousel';
import { TagModule } from 'primeng/tag';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FullCalendarModule } from '@fullcalendar/angular'; 
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { TabViewModule } from 'primeng/tabview';
import { DropdownModule } from 'primeng/dropdown';
import { RegistrationPasswordComponent } from './pages/registration-password/registration-password.component';
import { AuthInterceptor } from './authentication/auth/interceptor/auth-interceptor.interceptor';
import { PatientComponent } from './pages/dashboard/patient/patient.component';
import { PsychologistComponent } from './pages/dashboard/psychologist/psychologist.component';
import { AdminComponent } from './pages/dashboard/admin/admin.component';
import { HasRoleDirective } from './shared/directive/has-role.directive';
import { UserComponent } from './component/admin/user/user.component';
import { ProfileComponent } from './component/admin/profile/profile.component';
import { ReservationBoxComponent } from './component/admin/reservation-box/reservation-box.component';
import { ScheduledAppointmentComponent } from './component/admin/scheduled-appointment/scheduled-appointment.component';
import { TableModule } from 'primeng/table';
import { ReservationStatusPipe } from './shared/pipe/reservation-status.pipe';
import { RoleTranslatePipe } from './shared/pipe/role-translate.pipe';
import { InputSwitchModule } from 'primeng/inputswitch';
import { CalendarPsycologistComponent } from './component/psychologist/calendar-psycologist/calendar-psycologist.component';
import { ReservationPsycologistComponent } from './component/psychologist/reservation-psycologist/reservation-psycologist.component';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ConfirmationService } from 'primeng/api';
import { BankInfoComponent } from './component/bank-info/bank-info.component';
import { CalendarPatientComponent } from './component/patient/calendar-patient/calendar-patient.component';

@NgModule({
  declarations: [
    AppComponent,
    NavbarPageComponent,
    HomePageComponent,
    AboutMePageComponent,
    ContactPageComponent,
    BoxRentalPageComponent,
    ReservationPageComponent,
    AdminPageComponent,
    ConfirmationPageComponent,
    BoxConfirmationPageComponent,
    SuccessfulPaymentPageComponent,
    BoxSuccessfulPaymentPageComponent,
    LoginComponent,
    DashboardComponent,
    PasswordResetInitComponent,
    RegistrationPasswordComponent,
    PatientComponent,
    PsychologistComponent,
    AdminComponent,
    HasRoleDirective,
    UserComponent,
    ProfileComponent,
    ReservationBoxComponent,
    ScheduledAppointmentComponent,
    ReservationStatusPipe,
    RoleTranslatePipe,
    CalendarPsycologistComponent,
    ReservationPsycologistComponent,
    BankInfoComponent,
    CalendarPatientComponent,
    
  ],
  imports: [
    FullCalendarModule,
    BrowserModule,
    TabViewModule, 
    HttpClientModule,
    AppRoutingModule,
    CardModule,
    FileUploadModule,
    ButtonModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule, 
    AccordionModule,
    PanelModule,
    DialogModule,
    CheckboxModule,  
    ButtonModule,    
    RippleModule,     
    MenubarModule,
    CalendarModule,
    ToastModule,
    CarouselModule,
    TagModule,
    DropdownModule,
    TableModule,
    InputSwitchModule,
    ConfirmDialogModule,
  ],  
  providers: [
    provideHttpClient(withFetch()), 
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    MessageService, ConfirmationService,
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
