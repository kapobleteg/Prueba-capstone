import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'reservationStatus'
})
export class ReservationStatusPipe implements PipeTransform {

  transform(value: string): string {
    const translations: { [key: string]: string } = {
      'PENDING': 'Pendiente',
      'APPROVED': 'Aprobado',
      'REJECTED': 'Rechazado'
    };

    return translations[value] || value;
  }
}
