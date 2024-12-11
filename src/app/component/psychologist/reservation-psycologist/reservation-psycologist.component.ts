import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Reserve } from '../../../model/reserve.model';
import { ReservationService } from '../../../services/reservation-service/reservation.service';
import { AuthServerProvider } from '../../../authentication/auth/service/auth-jwt.service';
import { Reservation } from '../../../model/reservation.model';

@Component({
  selector: 'app-reservation-psycologist',
  templateUrl: './reservation-psycologist.component.html',
  styleUrl: './reservation-psycologist.component.scss'
})
export class ReservationPsycologistComponent {
  public reservations$: Observable<Reservation[]>;
  showDialog = false;
  showImageDialog = false;

  selectedReservation: any;

  constructor(private reservationService: ReservationService, private authService: AuthServerProvider){

    this.reservations$ = reservationService.getAllReservation(authService.getId());
  }

  openDialog(reservation: any): void {
    this.selectedReservation = reservation;
    this.showDialog = true;
  }
  
  acceptReservation(reservation: any): void {
    const reservationDTO = { id: reservation.id };
    this.reservationService.acceptReservation(reservationDTO).subscribe({
      next: () => {
        this.showDialog = false;
        alert('Reserva aceptada exitosamente');
        this.refreshReservations();
      },
      error: () => alert('Error al aceptar la reserva'),
    });
  }

  rejectReservation(reservation: any): void {
    const reservationDTO = { id: reservation.id }; 
    this.reservationService.rejectReservation(reservationDTO).subscribe({
      next: () => {
        this.showDialog = false;
        alert('Reserva rechazada exitosamente');
        this.refreshReservations();
      },
      error: () => alert('Error al rechazar la reserva'),
    });
  }

  getImageSrc(attached: string | null | undefined): string {
    return attached ? `data:image/jpeg;base64,${attached}` : '';
  }
  
  refreshReservations(): void {
    this.reservations$ = this.reservationService.getAll();
  }

}
