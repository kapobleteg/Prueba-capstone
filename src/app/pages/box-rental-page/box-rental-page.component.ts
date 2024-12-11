import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { BoxService } from '../../services/box-service/box.service';
import { Observable } from 'rxjs';
import { Box } from '../../model/box.model';

@Component({
  selector: 'app-box-rental-page',
  templateUrl: './box-rental-page.component.html',
  styleUrls: ['./box-rental-page.component.scss']
})
export class BoxRentalPageComponent implements OnInit {
  public boxes$!: Observable<Box[] | undefined>

  constructor(private router: Router, private boxService: BoxService) { }
  ngOnInit(): void {
    this.boxes$ = this.boxService.getAll();
  }

  onReserve(box: Box): void {
    this.router.navigate(['confirmar-box', box.id]);
  }

}
