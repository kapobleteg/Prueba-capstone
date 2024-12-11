import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Reservation } from '../../../model/reservation.model';
import { ReservationService } from '../../../services/reservation-service/reservation.service';

@Component({
  selector: 'app-reservation-box',
  templateUrl: './reservation-box.component.html',
  styleUrl: './reservation-box.component.scss'
})
export class ReservationBoxComponent {
  showDialog = false;
  showImageDialog = false;

  selectedReservation: any;
  public reservations$: Observable<Reservation[]>;

  constructor(private reservationService: ReservationService) {
    this.reservations$ = reservationService.getAll();
  }

  openDialog(reservation: any): void {
    this.selectedReservation = reservation;
    this.showDialog = true;
  }

  acceptReservation(reservation: any): void {
    const reservationDTO = { id: reservation.id }; // Ajusta según tu DTO en el backend
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
    const reservationDTO = { id: reservation.id }; // Ajusta según tu DTO en el backend
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