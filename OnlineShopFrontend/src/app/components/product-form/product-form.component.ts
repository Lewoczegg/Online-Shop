import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { ProductService } from '../../services/product.service';
import { Router } from '@angular/router';
import { Product } from '../../models/product.model';
import { HeaderComponent } from '../header/header.component';

@Component({
  selector: 'app-product-form',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent],
  templateUrl: './product-form.component.html',
  styleUrl: './product-form.component.css',
})
export class ProductFormComponent implements OnInit {
  productForm: FormGroup;
  productId: number | null = null;
  isEditMode: boolean = false;
  allertMessage: string = '';

  constructor(
    private fb: FormBuilder,
    private productService: ProductService,
    private router: Router
  ) {
    this.productForm = this.fb.group({
      name: [
        '',
        [
          Validators.required,
          Validators.minLength(1),
          Validators.maxLength(100),
        ],
      ],
      description: ['', [Validators.required, Validators.maxLength(500)]],
      quantity: [0, [Validators.required, Validators.min(0)]],
      price: [0.0, [Validators.required, Validators.min(0)]],
      isActive: [true, [Validators.required]],
    });
  }

  ngOnInit(): void {
    const productData = history.state.product as Product;
    if (productData) {
      this.isEditMode = true;
      this.productId = productData.id;
      this.productForm.patchValue(productData);
    }
  }

  onSubmit(): void {
    if (this.productForm.valid) {
      const product: Product = this.productForm.value;

      if (this.isEditMode && this.productId) {
        product.id = this.productId;
        this.productService.updateProduct(product).subscribe(() => {
          this.allertMessage = 'Product deleted successfully';
          setTimeout(() => {
            this.allertMessage = '';
            this.router.navigate(['/manage-products']);
          }, 1500);
        });
      } else {
        this.productService.addProduct(product).subscribe(() => {
          this.allertMessage = 'Product deleted successfully';
          setTimeout(() => {
            this.allertMessage = '';
            this.router.navigate(['/manage-products']);
          }, 1500);
        });
      }
    }
  }

  onCancel(): void {
    this.router.navigate(['/manage-products']);
  }
}
