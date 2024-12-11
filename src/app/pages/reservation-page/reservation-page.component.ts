import { Component } from '@angular/core';
import { Router } from '@angular/router';  

@Component({
  selector: 'app-reservation-page',
  templateUrl: './reservation-page.component.html',
  styleUrls: ['./reservation-page.component.scss']
})
export class ReservationPageComponent {
  reservationData = {
    address: 'Doctor Manuel Barros Borgoño 110, Providencia',
    motivo: '',
    prevision: '',
    primeraVisita: '',
    hora: '',
    fecha: null  
  };

  availableSlots: string[] = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00'];  

  constructor(private router: Router) {}  

  onSubmit() {
    if (this.reservationData.motivo && this.reservationData.hora && this.reservationData.fecha) {
      
      setTimeout(() => {
        this.router.navigate(['/confirmation']);  
      }, 500);  
    }
  }
}
