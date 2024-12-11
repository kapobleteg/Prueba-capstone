import { Component } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { BankInfoService } from '../../services/bank-info-service/bank-info.service';
import { AuthServerProvider } from '../../authentication/auth/service/auth-jwt.service';

@Component({
  selector: 'app-bank-info',
  templateUrl: './bank-info.component.html',
  styleUrl: './bank-info.component.scss'
})
export class BankInfoComponent {
  bankForm: FormGroup;

  constructor(private fb: FormBuilder, private bankInfoService: BankInfoService, private authService: AuthServerProvider) {
    this.bankForm = this.fb.group({
      id: [null],
      name: ['', Validators.required],
      bank: ['', Validators.required],
      rut: ['', Validators.required],
      accountType: ['', Validators.required],
      accountNumber: ['', Validators.required],
      amountTransfer: [0, Validators.required],
      reasonTransfer: ['', Validators.required],
    });
  }

  ngOnInit() {
    this.loadBankInfo();
  }

  loadBankInfo() {
    const userId = this.authService.getId();
    this.bankInfoService.getById(userId).subscribe((data) => {
      this.bankForm.patchValue(data);
    });
  }

  save() {
    if (this.bankForm.value.id) {
      this.bankInfoService.update(this.bankForm.value).subscribe(() => {
        alert('Información actualizada');
      });
    } else {
      this.bankInfoService.create(this.bankForm.value).subscribe(() => {
        alert('Información creada');
      });
    }
  }

  delete() {
    if (this.bankForm.value.id) {
      const id = this.bankForm.value.id;
      this.bankInfoService.delete(id).subscribe(() => {
        alert('Información eliminada');
        this.bankForm.reset();
      });
    }
  }
}