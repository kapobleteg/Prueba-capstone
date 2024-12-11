import { Component } from '@angular/core';
import { Observable } from 'rxjs';
import { Profile } from '../../../model/profile.model';
import { ProfileService } from '../../../services/profile-service/profile.service';
import { AuthServerProvider } from '../../../authentication/auth/service/auth-jwt.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent {
public profile$: Observable<Profile>;

constructor(private profileService: ProfileService, private authService: AuthServerProvider){
  this.profile$ = profileService.getById(authService.getId());
}

 }
