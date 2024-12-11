import { User } from "./user.model";

export interface BankInfo {
    id: number;
    name: string;
    bank: string;
    rut: string;
    accountType: string;
    accountNumber: string;
    amountTransfer: number;
    reasonTransfer: string;
    user: User;
  }