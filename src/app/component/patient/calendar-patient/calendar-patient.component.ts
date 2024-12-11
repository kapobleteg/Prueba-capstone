import { Component } from '@angular/core';
import { CalendarOptions } from '@fullcalendar/core';
import { MessageService, ConfirmationService } from 'primeng/api';
import { Observable, map } from 'rxjs';
import { AuthServerProvider } from '../../../authentication/auth/service/auth-jwt.service';
import { Reservation } from '../../../model/reservation.model';
import { ReservationService } from '../../../services/reservation-service/reservation.service';
import timeGridPlugin from '@fullcalendar/timegrid';
import interactionPlugin from '@fullcalendar/interaction';
import esLocale from '@fullcalendar/core/locales/es';

@Component({
  selector: 'app-calendar-patient',
  templateUrl: './calendar-patient.component.html',
  styleUrl: './calendar-patient.component.scss'
})
export class CalendarPatientComponent {
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
    this.reservations$ = this.reservationService.getAllPatient(
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
      this.showMessage('warn', 'Movimiento inválido', 'No puedes mover el evento a una fecha/hora pasada.');
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
          },
          error: () => {
            info.revert();
            this.showMessage('error', 'Error de actualización', 'Error al actualizar la reserva. Intenta de nuevo.');
          },
        });
      },
      reject: () => {
        info.revert();
        this.showMessage('info', 'Movimiento Cancelado', 'El evento no se ha movido.');
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

  onCancelReservation() {
    if (!this.selectedReservation || !this.selectedReservation.id) {
      this.showMessage('warn', 'Error', 'No se ha seleccionado ninguna cita para cancelar.');
      return;
    }
  
    this.confirmationService.confirm({
      message: '¿Está seguro de que desea cancelar esta cita?',
      header: 'Confirmación de Cancelación',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        const reservationId = this.selectedReservation.id;
  
        this.reservationService.cancelReservation(reservationId).subscribe({
          next: () => {
            this.displayDialog = false;
            this.reservations$ = this.reservationService.getAllPatient(this.authService.getId());
            this.showMessage('success', 'Cita Cancelada', 'La cita ha sido cancelada exitosamente.');
          },
          error: () => {
            this.showMessage('error', 'Error', 'Hubo un problema al cancelar la cita.');
          },
        });
      },
      reject: () => {
        this.showMessage('info', 'Cancelación Rechazada', 'La cita no se ha cancelado.');
      },
    });
  }
  
  }