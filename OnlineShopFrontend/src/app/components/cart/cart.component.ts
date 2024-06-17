import { Component } from '@angular/core';
import { CartStoreItem } from '../../store/cartStoreItem';
import { User } from '../../models/user.model';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { faTrash } from '@fortawesome/free-solid-svg-icons';
import { Router } from '@angular/router';
import { UserService } from '../../services/user.service';
import { CartItem } from '../../models/cart.model';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-cart',
  standalone: true,
  imports: [
    FontAwesomeModule,
    CommonModule,
    ReactiveFormsModule,
    HeaderComponent,
  ],
  templateUrl: './cart.component.html',
  styleUrl: './cart.component.css',
})
export class CartComponent {
  faTrash = faTrash;
  orderForm: FormGroup;
  user: User;
  alertType: number = 0;
  alertMessage: string = '';
  disableCheckout: boolean = false;

  constructor(
    public cartStoreItem: CartStoreItem,
    private router: Router,
    private formBuilder: FormBuilder,
    private userService: UserService
  ) {
    this.user = this.userService.getUser();

    this.orderForm = this.formBuilder.group({
      name: [
        this.user.firstName == undefined
          ? ''
          : this.user.firstName + ' ' + this.user.lastName,
        Validators.required,
      ],
      address: [this.user.address, Validators.required],
      mobileNumber: [this.user.mobileNumber, Validators.required],
    });
  }

  navigateToHome(): void {
    this.router.navigate(['/']);
  }

  updateQuantity($event: any, cartItem: CartItem): void {
    if ($event.target.innerText === '+') {
      this.cartStoreItem.addProduct(cartItem.product);
    } else if ($event.target.innerText === '-') {
      this.cartStoreItem.decreaseProductQuantity(cartItem);
    }
  }

  removeItem(cartItem: CartItem): void {
    this.cartStoreItem.removeProduct(cartItem);
  }

  onSubmit(): void {
    if (this.user.authStatus) {
    } else {
      this.alertType = 1;
      this.alertMessage = 'Please log in to register your order.';
    }
  }
}
