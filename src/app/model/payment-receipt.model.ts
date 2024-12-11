export interface PaymentReceipt{
    id:number;
    attached:string;
    amount:number;
    date:Date;
    status:boolean;
    comments?:string;
}