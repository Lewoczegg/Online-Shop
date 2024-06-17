import { Component } from '@angular/core';
import { Product } from '../../models/product.model';
import { ActivatedRoute } from '@angular/router';
import { ProductService } from '../../services/product.service';
import { CurrencyPipe } from '@angular/common';
import { CartStoreItem } from '../../store/cartStoreItem';

@Component({
  selector: 'app-product-detail',
  standalone: true,
  imports: [CurrencyPipe],
  templateUrl: './product-detail.component.html',
  styleUrl: './product-detail.component.css',
})
export class ProductDetailComponent {
  product: Product | null = null;

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private cartStoreItem: CartStoreItem
  ) {}

  ngOnInit(): void {
    const productId = +this.route.snapshot.paramMap.get('id')!;
    if (productId) {
      this.productService.getProductById(productId).subscribe((product) => {
        this.product = product;
      });
    }
  }

  addToCart(profuctToAdd: Product): void {
    this.cartStoreItem.addProduct(profuctToAdd);
  }
}
