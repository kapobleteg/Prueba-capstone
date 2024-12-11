export interface BookAppointmentDTO {
    email: string;
    phone: string;
    name: string;
    reservationDate: Date;
    endDate: Date;
    file: string;
    price: number;
    psychologist: Psychologist;
  }

  export interface Psychologist {
    id: number;
    name: string;
  }