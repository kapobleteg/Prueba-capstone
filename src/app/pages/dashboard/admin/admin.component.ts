import { Component } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import { Reservation } from '../../../model/reservation.model';
import { ReservationService } from '../../../services/reservation-service/reservation.service';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';



@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrl: './admin.component.scss'
})
export class AdminComponent {
  public reservation!: Reservation[];

  calendarOptions: CalendarOptions = {
    plugins: [timeGridPlugin, interactionPlugin],
    initialView: 'timeGridWeek',
    headerToolbar: {
      left: 'prev',
      center: 'title',
      right: 'next,timeGridWeek,timeGridDay',
    },
    allDaySlot: false,
    nowIndicator: true,
    height: '100%',
    events: [], 
    locale: esLocale,
    editable: true,
  };

  eventos = [
    { usuario: 'Usuario1', fecha: '2024-10-24', horaInicio: '09:00', horaFin: '10:00' },
    { usuario: 'Usuario2', fecha: '2024-10-24', horaInicio: '10:00', horaFin: '11:00' },
    { usuario: 'Usuario3', fecha: '2024-10-24', horaInicio: '14:00', horaFin: '15:00' },
    { usuario: 'Usuario4', fecha: '2024-10-25', horaInicio: '09:00', horaFin: '10:00' },
  ];


  constructor(private reservationService: ReservationService) { }

  ngOnInit() {
    this.reservationService.getAll().subscribe({
      next: (reservations: Reservation[]) => {
        this.reservation = reservations;
        this.calendarOptions.events = this.transformarEventos(this.reservation);
      },
      error: (error) => {
        console.error('Error fetching reservations:', error);
      },
    });
  }

  transformarEventos(reservations: Reservation[]) {
    return reservations.map((res) => ({
      title: res.user.name, 
      start: new Date(res.startDate).toISOString(),
      end: new Date(res.endDate).toISOString(),
    }));
  }


}
