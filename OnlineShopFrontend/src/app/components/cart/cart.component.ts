import { Component } from '@angular/core';
import { CartStoreItem } from '../../store/cartStoreItem';
import { User } from '../../models/user.model';
import {
  AbstractControl,
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
import { Order } from '../../models/order.model';
import { OrderService } from '../../services/order.service';

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
    private userService: UserService,
    private orderService: OrderService
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

  get name(): AbstractControl<any, any> | null {
    return this.orderForm.get('name');
  }

  get address(): AbstractControl<any, any> | null {
    return this.orderForm.get('address');
  }

  navigateToHome(): void {
    this.router.navigate(['/home']);
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
      const order: Order = {
        address: this.address?.value,
        orderDetails: this.cartStoreItem.cart.products.map((product) => {
          return {
            name: product.product.name,
            description: product.product.description,
            quantity: product.quantity,
            price: product.amount,
          };
        }),
      };

      this.orderService.placeOrder(order).subscribe({
        next: (response) => {
          this.cartStoreItem.clearCart();
          this.alertType = 0;
          this.alertMessage = 'Order registered successfully';
          this.disableCheckout = true;
        },
        error: (error) => {
          this.alertType = 2;
          this.alertMessage = 'Please log in to register your order.';
        },
      });
    } else {
      this.alertType = 1;
      this.alertMessage = 'Please log in to register your order.';
    }
  }
}
