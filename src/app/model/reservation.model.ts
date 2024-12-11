import { Box } from "./box.model";
import { PaymentReceipt } from "./payment-receipt.model";
import { Psychologist } from "./psychologist.model";
import { User } from "./user.model";

export interface Reservation {
    id:number;
    user:User;
    box:Box;
    startDate:Date;
    endDate: Date;
    status:ReservationStatus;
    paymentReceipt: PaymentReceipt;
    psychologist?: Psychologist;
}

export const STATUS = ['PENDING', 'APPROVED', 'REJECTED'];
export type ReservationStatus = typeof STATUS[number]