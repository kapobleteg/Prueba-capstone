import { Profile } from "./profile.model";
import { Role } from "./role.model";

export interface User{
    id:number;
    name: string;
    email: string;
    phone: string;
    profile: Profile;
    role: Role;
    status: boolean;
    password_hash?: string;
}