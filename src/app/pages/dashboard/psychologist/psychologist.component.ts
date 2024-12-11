import { Component, ViewChild } from '@angular/core';
import { ReservationPsycologistComponent } from '../../../component/psychologist/reservation-psycologist/reservation-psycologist.component';

@Component({
  selector: 'app-psychologist',
  templateUrl: './psychologist.component.html',
  styleUrl: './psychologist.component.scss'
})
export class PsychologistComponent {
  @ViewChild('reservationPsycologist') reservationComponent!: ReservationPsycologistComponent;

  onReservationUpdated() {
    this.reservationComponent.refreshReservations();
  }
}
