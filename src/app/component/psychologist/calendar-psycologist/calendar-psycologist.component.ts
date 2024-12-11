import { Component, EventEmitter, Output } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';
import { ReservationService } from '../../../services/reservation-service/reservation.service';
import { AuthServerProvider } from '../../../authentication/auth/service/auth-jwt.service';
import { Reservation } from '../../../model/reservation.model';
import { map, Observable } from 'rxjs';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'app-calendar-psycologist',
  templateUrl: './calendar-psycologist.component.html',
  styleUrls: ['./calendar-psycologist.component.scss']
})
export class CalendarPsycologistComponent {
  @Output() reservationUpdated = new EventEmitter<void>();
  public reservations$: Observable<Reservation[]>;
  displayDialog: boolean = false;
  selectedReservation: any = null;

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

  constructor(
    private reservationService: ReservationService,
    private authService: AuthServerProvider,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {
    this.reservations$ = this.reservationService.getAllReservation(
      this.authService.getId()
    );

    this.reservations$
      .pipe(
        map((reservations: Reservation[]) =>
          reservations.map(reservation => ({
            id: reservation.id.toString(),
            title: reservation.user.name,
            start: reservation.startDate,
            end: reservation.endDate,
            extendedProps: {
              status: reservation.status,
              box: reservation?.box?.name,
              amount: reservation.paymentReceipt?.amount,
            },
          }))
        )
      )
      .subscribe(events => {
        this.calendarOptions = {
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
          events: events,
          locale: esLocale,
          editable: true,
          eventClick: (info) => this.onEventClick(info),
          eventDrop: (info) => this.confirmEventDrop(info), 
        };
      });
  }

  onEventClick(info: any) {
    this.selectedReservation = {
      title: info.event.title,
      start: info.event.start,
      end: info.event.end,
      status: info.event.extendedProps.status,
      box: info.event.extendedProps.box,
      amount: info.event.extendedProps.amount,
    };
    this.displayDialog = true;
  }
  confirmEventDrop(info: any) {
    const movedEvent = info.event;
    const newStart = movedEvent.start;
    const newEnd = movedEvent.end;
    const now = new Date();
  
    if (newStart && newStart < now) {
      info.revert();
      this.showMessage('warn', 'Movimiento inválido', 'No puedes mover la reserva a una fecha/hora pasada.');
      return;
    }
  
    this.confirmationService.confirm({
      message: '¿Está seguro de mover la reserva a otra fecha?',
      header: 'Confirmación de Movimiento',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const reservationId = movedEvent.id;
        const payload = {
          startDate: newStart.toISOString(),
          endDate: newEnd.toISOString(),
        };
  
        this.reservationService.updateReservation(reservationId, payload).subscribe({
          next: () => {
            this.reservations$ = this.reservationService.getAllReservation(
              this.authService.getId()
            );
            this.showMessage('success', 'Actualización exitosa', 'Reserva actualizada correctamente.');
            this.reservationUpdated.emit();
          },
          error: () => {
            info.revert();
            this.showMessage('error', 'Error de actualización', 'Error al actualizar la reserva. Intenta de nuevo.');
          },
        });
      },
      reject: () => {
        info.revert();
        this.showMessage('info', 'Movimiento Cancelado', 'La reserva no se ha movido.');
      },
    });
  }
  
  showMessage(state: string, title: string, description: string) {
    this.messageService.add({
      severity: state,
      summary: title,
      detail: description,
    });
  }
  }