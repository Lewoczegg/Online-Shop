import { Component, Input } from '@angular/core';
import { Product } from '../../models/product.model';
import { CurrencyPipe } from '@angular/common';
import { CartStoreItem } from '../../store/cartStoreItem';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-product-card',
  standalone: true,
  imports: [CurrencyPipe, RouterLink],
  templateUrl: './product-card.component.html',
  styleUrl: './product-card.component.css',
})
export class ProductCardComponent {
  @Input() product!: Product;

  constructor(private cartStoreItem: CartStoreItem) {}

  truncateDescription(description: string): string {
    const maxLength = 50;
    return description.length > maxLength
      ? description.substring(0, maxLength) + '...'
      : description;
  }

  addToCart(profuctToAdd: Product): void {
    this.cartStoreItem.addProduct(profuctToAdd);
  }
}
