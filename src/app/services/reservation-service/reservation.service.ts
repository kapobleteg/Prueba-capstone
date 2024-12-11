import { Injectable } from '@angular/core';
import { Reservation } from '../../model/reservation.model';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Reserve } from '../../model/reserve.model';
import { BookAppointmentDTO } from '../../model/schedule.model';

const API_URL = `${environment.api}/reservation`


@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  getById(id: number | undefined): Observable<Reservation> {
    return this.http.get<Reservation>(API_URL + '/' + id);

  }
  getAll(): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(API_URL)
  }

  reservation(reserve:Reserve): Observable<Reservation> {
    return this.http.post<Reservation>(API_URL+"/reservation-box",reserve)
  }
  
  schedule(reserve:Reserve): Observable<BookAppointmentDTO> {
    return this.http.post<BookAppointmentDTO>(API_URL+"/schedule",reserve)
  }

  acceptReservation(dto: any) {
    return this.http.post(API_URL+"/accepted", dto);
  }

  rejectReservation(dto: any) {
    return this.http.post(API_URL+"/rejected", dto);
  }

  getAllReservation(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(API_URL+"/book-appointments/"+id)
  }

  getAllPatient(id: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(API_URL+"/patient/"+id)
  }

  updateReservation(id: string, payload: { startDate: string; endDate: string }): Observable<any> {
    return this.http.put(API_URL+"/"+id, payload);
  }

  cancelReservation(id: string): Observable<void> {
    return this.http.get<void>(API_URL+"/cancel/"+id);
  }
}
