import { Component } from '@angular/core';
import { HeaderComponent } from '../header/header.component';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { UserService } from '../../services/user.service';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-become-seller',
  standalone: true,
  imports: [HeaderComponent, RouterModule, CommonModule, ReactiveFormsModule],
  templateUrl: './become-seller.component.html',
  styleUrl: './become-seller.component.css',
})
export class BecomeSellerComponent {
  becomeSellerForm: FormGroup;
  alertMessage: string = '';
  alertCode: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.becomeSellerForm = this.formBuilder.group({
      iban: ['', [Validators.required]],
      address: ['', [Validators.required, Validators.minLength(10)]],
      dateOfBirth: ['', [Validators.required]],
    });
  }

  get iban(): AbstractControl<any, any> | null {
    return this.becomeSellerForm.get('iban');
  }

  get address(): AbstractControl<any, any> | null {
    return this.becomeSellerForm.get('address');
  }

  get dateOfBirth(): AbstractControl<any, any> | null {
    return this.becomeSellerForm.get('dateOfBirth');
  }

  onSubmit(): void {
    const user: User = this.userService.getUser();

    user.iban = this.iban?.value;
    user.address = this.address?.value;
    user.dateOfBirth = this.dateOfBirth?.value;

    this.userService.becomeSeler(user).subscribe({
      next: (result) => {
        this.alertMessage =
          'Successfully become a seller. Login again to see changes';
        this.alertCode = result?.code;

        setTimeout(() => {
          window.sessionStorage.setItem('userdetails', '');
          window.sessionStorage.removeItem('Authorization');
          this.router.navigate(['login']);
        }, 2000);
      },
      error: (error) => {
        this.alertMessage = error.error?.message;
        this.alertCode = error.error?.code;
      },
    });
  }
}
