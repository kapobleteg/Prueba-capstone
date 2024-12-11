import { User } from "./user.model";


export interface Profile{
    id:number;
    user: User;
    specialty: string;
    certifications: string[];
}