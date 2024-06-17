import { Product } from './product.model';

export interface CartItem {
  product: Product;
  quantity: number;
  amount: number;
}

export interface Cart {
  products: CartItem[];
  totalQuantity: number;
  totalAmount: number;
}
