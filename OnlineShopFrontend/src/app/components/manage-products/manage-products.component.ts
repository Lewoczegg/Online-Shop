import { Component, OnInit } from '@angular/core';
import { Product } from '../../models/product.model';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { CurrencyPipe } from '@angular/common';
import { HeaderComponent } from '../header/header.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-manage-products',
  standalone: true,
  imports: [CurrencyPipe, HeaderComponent],
  templateUrl: './manage-products.component.html',
  styleUrl: './manage-products.component.css',
})
export class ManageProductsComponent implements OnInit {
  products: Product[] = [];
  allertMessage: string = '';

  constructor(private productServie: ProductService, private router: Router) {}

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts(): void {
    this.productServie.getMyProducts().subscribe((data) => {
      this.products = data;
    });
  }

  addProduct(): void {
    this.router.navigate(['/add-product']);
  }

  editProduct(product: Product): void {
    this.router.navigate(['/edit-product', product.id], { state: { product } });
  }

  deleteProduct(id: number): void {
    this.productServie.deleteProduct(id).subscribe(() => {
      this.loadProducts();
      this.allertMessage = 'Product deleted successfully';
      setTimeout(() => {
        this.allertMessage = '';
      }, 1500);
    });
  }
}
