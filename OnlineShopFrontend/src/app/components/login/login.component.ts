import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { UserService } from '../../services/user.service';
import { User } from '../../models/user.model';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterModule, ReactiveFormsModule, CommonModule, HeaderComponent],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
})
export class LoginComponent {
  userLoginForm: FormGroup;
  allertMessage: string = '';
  allertCode: number = 0;
  user: User = {
    id: 0,
    firstName: '',
    lastName: '',
    email: '',
    password: '',
  };

  constructor(
    private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {
    this.userLoginForm = this.formBuilder.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
    });
  }

  onSubmit(): void {
    this.user.email = this.email?.value;
    this.user.password = this.password?.value;

    this.userService.login(this.user).subscribe({
      next: (result) => {
        this.allertMessage = 'Login successful!';
        this.allertCode = result.status;
        window.sessionStorage.setItem(
          'Authorization',
          result.headers.get('Authorization')!
        );
        this.user = <any>result.body;
        this.user.authStatus = 'AUTH';
        window.sessionStorage.setItem('userdetails', JSON.stringify(this.user));

        const lastUrl = sessionStorage.getItem('lastUrl') || '/home';

        setTimeout(() => {
          this.router.navigate([lastUrl]);
          sessionStorage.removeItem('lastUrl');
        }, 1500);
      },
      error: (error) => {
        this.allertMessage = error.error?.message;
        this.allertCode = error.error?.code;
        window.sessionStorage.setItem('userdetails', '');
      },
    });
  }

  get email(): AbstractControl<any, any> | null {
    return this.userLoginForm.get('email');
  }

  get password(): AbstractControl<any, any> | null {
    return this.userLoginForm.get('password');
  }
}
