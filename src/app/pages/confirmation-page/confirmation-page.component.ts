import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Psychologist } from '../../model/psychologist.model';
import { PsychologistService } from '../../services/psychologist-service/psychologist.service';
import { Observable } from 'rxjs';
import { ReservationService } from '../../services/reservation-service/reservation.service';
import { MessageService } from 'primeng/api';
import { BookAppointmentDTO } from '../../model/schedule.model';
import { ActivatedRoute, Router } from '@angular/router';
import { BankInfoService } from '../../services/bank-info-service/bank-info.service';
import { BankInfo } from '../../model/bank-info.model';

@Component({
  selector: 'app-confirmation-page',
  templateUrl: './confirmation-page.component.html',
  styleUrls: ['./confirmation-page.component.scss'],
  providers: [MessageService]
})
export class ConfirmationPageComponent implements OnInit {
  form: FormGroup;
  formModel: FormModel;
  loading = false;
  minDate: Date = new Date();
  psychologists$!: Observable<Psychologist[]>;
  bankInfo$!: Observable<BankInfo>;
  bankInfo!: BankInfo;

  constructor(private fb: FormBuilder,
    private psychologistService: PsychologistService,
    private reservationService: ReservationService,
    private messageService: MessageService,
    private route: Router,
    private router: ActivatedRoute,
    private bankInfoService: BankInfoService) {

    this.formModel = {
      name: fb.control(null),
      email: fb.control(null),
      phone: fb.control(null),
      psychologist: fb.control(null),
      reservationDate: fb.control(null),
      file: fb.control(null)
    }

    this.form = fb.group(this.formModel)


  }
  
  ngOnInit() {
    this.psychologists$ = this.psychologistService.getAll();
  }

  public onSubmit(): void {
    if (this.form.valid) {
      const selectedDate = new Date(this.form.value.reservationDate);
      selectedDate.setMinutes(0, 0, 0);

      const reservation = {
        ...this.form.value,
        startDate: selectedDate,
        endDate: new Date(selectedDate.getTime() + 60 * 60 * 1000)
      };

      this.loading = true;
      this.reservationService.schedule(reservation).subscribe({
        next: (reservation: BookAppointmentDTO) => {
          this.showMessage('success', 'Reserva Exitosa', 'Su reserva ha sido registrada con éxito. Por favor, revise su correo electrónico para más detalles.');
          setTimeout(() => {
            this.route.navigate(['/home']);
            this.loading = false;
          }, 3000); 
        },
        error: (error: any) => {
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

  onFileSelected(event: any): void {
    const file: File = event.files[0];
    const reader = new FileReader();

    reader.onload = () => {
      const result = reader.result as string;
      const base64String = result.split(',')[1]?.trim();

      this.form.patchValue({
        file: base64String
      });
    };

    reader.onerror = (error) => {
      console.error('Error al leer el archivo:', error);
    };
    reader.readAsDataURL(file);
  }


  showMessage(state: string, title: string, description: string) {
    this.messageService.add({
      severity: state,
      summary: title,
      detail: description
    });
  }


  selectPsychologist() {
    const psychologist: Psychologist = this.form.value.psychologist;
    if (psychologist) {
      const userId = psychologist?.user.id;
      if (userId)
        this.bankInfoService.getById(userId).subscribe((resp) => { this.bankInfo = resp });
    }
  }
}

interface FormModel {
  name: FormControl<string | null>;
  email: FormControl<string | null>;
  phone: FormControl<string | null>;
  psychologist: FormControl<Psychologist | null>;
  reservationDate: FormControl<string | null>;
  file: FormControl<any | null>;
}