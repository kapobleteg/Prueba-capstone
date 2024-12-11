import { Component, OnDestroy, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BoxService } from '../../services/box-service/box.service';
import { Box } from '../../model/box.model';
import { map, Observable, Subject, switchMap, takeUntil, tap } from 'rxjs';
import { MessageService } from 'primeng/api';
import { ReservationService } from '../../services/reservation-service/reservation.service';
import { Reservation } from '../../model/reservation.model';
import { BankInfoService } from '../../services/bank-info-service/bank-info.service';
import { BankInfo } from '../../model/bank-info.model';

@Component({
  selector: 'app-box-confirmation-page',
  templateUrl: './box-confirmation-page.component.html',
  styleUrls: ['./box-confirmation-page.component.scss'],
  providers: [MessageService]
})
export class BoxConfirmationPageComponent implements OnInit, OnDestroy {
  confirmationForm: FormGroup;
  private unsuscriber = new Subject<void>();
  public box$!: Observable<Box | undefined>;
  public bankInfo$: Observable<BankInfo | undefined>;
  public id: number | undefined;
  public loading: boolean = false;  
  public file: any;
  minDate: Date = new Date();
  disabledDates: Date[] = [];
  disabledHours: number[] = [];
  reservations: Reservation[] = [];

  transferData = {
    recipientName: 'Juan Pérez',
    recipientRUT: '12.345.678-9',
    bank: 'Banco de Chile',
    accountType: 'Cuenta Corriente',
    accountNumber: '1234567890',
    reason: 'Pago de servicios'
  };

  constructor(
    private router: ActivatedRoute,
    private fb: FormBuilder,
    private boxService: BoxService,
    private messageService: MessageService,
    private reservationService: ReservationService,
    private route: Router,
    private bankInfoService: BankInfoService
  ) {
    this.confirmationForm = this.fb.group({
      name: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', Validators.required],
      reservationDate: [null, Validators.required],
      boxId: [null, Validators.required],
      file: [null, Validators.required],
      specialty: ['', Validators.required],
      certifications: ['', Validators.required]
  });

    this.bankInfo$ = bankInfoService.getById(1);
    
  }

  ngOnInit(): void {
    this.loadDisabledDatesAndHours();
    this.router.paramMap
      .pipe(
        map((params) => Number(params.get('id'))),
        takeUntil(this.unsuscriber)
      )
      .subscribe((id: any) => {
        this.id = id;
        this.box$ = this.boxService.getById(this.id);
      });
      
  }

  ngOnDestroy(): void {
    this.unsuscriber.next();
    this.unsuscriber.complete();
  }

  loadDisabledDatesAndHours(): void {
    this.reservationService.getAll().subscribe((reservations) => {
      this.reservations = reservations;
      reservations.forEach((reservation) => {
        const startDate = new Date(reservation.startDate);
        const endDate = new Date(reservation.endDate);

        if (startDate.getHours() === 0 && endDate.getHours() === 23) {
          this.disabledDates.push(startDate);
        }

        if (startDate.toDateString() === new Date().toDateString()) {
          this.disabledHours.push(startDate.getHours());
        }
      });
    });
  }

  onDateSelect(event: Date): void {
    const selectedDate = new Date(event);
    selectedDate.setMinutes(0, 0, 0);
    const selectedHour = selectedDate.getHours();
  
    const isHourOccupied = this.reservations.some(reservation => {
      const start = new Date(reservation.startDate);
      const end = new Date(reservation.endDate);
  
      return (
        selectedDate.toDateString() === start.toDateString() && 
        selectedHour >= start.getHours() && 
        selectedHour < end.getHours()
      );
    });
  
    if (isHourOccupied) {
      this.showMessage('warn', 'Hora no disponible', 'Esta hora ya ha sido reservada.');
    } else {
      this.confirmationForm.patchValue({ reservationDate: selectedDate });
    }
  }

  onFileSelected(event: any): void {
    const file: File = event.files[0];
    const reader = new FileReader();
  
    reader.onload = () => {
      const result = reader.result as string;
      const base64String = result.split(',')[1]?.trim();
  
      this.confirmationForm.patchValue({
        file: base64String
      });
    };
  
    reader.onerror = (error) => {
      console.error('Error al leer el archivo:', error);
    };
    this.confirmationForm.patchValue({ boxId: this.id });
    reader.readAsDataURL(file);
  }

  public onSubmit(): void {
    if (this.confirmationForm.valid) {
        const { specialty, certifications, ...reservationData } = this.confirmationForm.value;

        const reservation = {
            ...reservationData,
            specialty: specialty,
            certifications: certifications.split(',').map((cert: string) => cert.trim())
        };

        this.loading = true;
        this.reservationService.reservation(reservation).subscribe({
          next: (reservation: Reservation) => {
            this.showMessage('success', 'Reserva Exitosa', 'Su reserva ha sido registrada con éxito. Por favor, revise su correo electrónico para más detalles.');
            setTimeout(() => {
              this.route.navigate(['/home']);
              this.loading = false;
            }, 3000); 
          },
            error: (error) => {
                console.error('Error al realizar la reserva:', error);
                const errorMessage = error.error?.message || 'Error al realizar la reserva';
                this.messageService.add({
                    severity: 'error',
                    summary: 'Error',
                    detail: errorMessage
                });
                this.loading = false;
            }
        });
    }
}
 


  showMessage(state: string, title: string, description: string){
    this.messageService.add({
      severity: state,
      summary: title,
      detail: description
    });
  }
  
}
