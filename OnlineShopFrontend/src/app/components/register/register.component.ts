import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { RouterModule, Router } from '@angular/router';
import { matchPasswords } from './validators/match-passwords.validator';
import { User } from '../../models/user.model';
import { UserService } from '../../services/user.service';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule, HeaderComponent],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css',
})
export class RegisterComponent {
  userRegisterForm: FormGroup;
  allertMessage: string = '';
  allertCode: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.userRegisterForm = this.formBuilder.group(
      {
        firstname: ['', Validators.required],
        lastname: ['', Validators.required],
        email: ['', [Validators.required, Validators.email]],
        phone: ['', [Validators.pattern('^\\+?[0-9. ()-]{7,25}$')]],
        password: ['', [Validators.required, Validators.minLength(8)]],
        confirmPassword: ['', Validators.required],
      },
      {
        validator: matchPasswords,
      }
    );
  }

  get firstname(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('firstname');
  }

  get lastname(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('lastname');
  }

  get email(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('email');
  }

  get phone(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('phone');
  }

  get password(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('password');
  }

  get confirmPassword(): AbstractControl<any, any> | null {
    return this.userRegisterForm.get('confirmPassword');
  }

  onSubmit(): void {
    const user: User = {
      firstName: this.firstname?.value,
      lastName: this.lastname?.value,
      email: this.email?.value,
      mobileNumber: this.phone?.value,
      password: this.password?.value,
    };

    this.userService.register(user).subscribe({
      next: (result) => {
        this.allertMessage = result?.message;
        this.allertCode = result?.code;
        if (this.allertCode === 201) {
          setTimeout(() => {
            this.router.navigate(['/login']);
          }, 1500);
        }
      },

      error: (error) => {
        this.allertMessage = error.error?.message;
        this.allertCode = error.error?.code;
      },
    });
  }
}
