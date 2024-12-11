import { User } from "./user.model";

export interface Psychologist{
    id: number;
    name: string;
    user: User;
}