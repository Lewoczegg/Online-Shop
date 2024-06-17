import { Observable } from 'rxjs';
import { Cart, CartItem } from '../models/cart.model';
import { StoreItem } from './storeItem';
import { Product } from '../models/product.model';

export class CartStoreItem extends StoreItem<Cart> {
  constructor() {
    const storedCart: any = sessionStorage.getItem('cart');

    if (storedCart) {
      super(JSON.parse(storedCart));
    } else {
      super({
        products: [],
        totalQuantity: 0,
        totalAmount: 0,
      });
    }
  }

  get cart$(): Observable<Cart> {
    return this.value$;
  }

  get cart(): Cart {
    return this.value;
  }

  addProduct(product: Product): void {
    const cartProduct: CartItem | undefined = this.cart.products.find(
      (cartProduct) => cartProduct.product.id === product.id
    );

    if (!cartProduct) {
      this.cart.products = [
        ...this.cart.products,
        { product: product, amount: Number(product.price), quantity: 1 },
      ];
    } else {
      cartProduct.quantity++;
      cartProduct.amount += Number(product.price);
    }
    this.cart.totalAmount += Number(product.price);
    ++this.cart.totalQuantity;
    this.saveCart();
  }

  removeProduct(cartItem: CartItem): void {
    this.cart.products = this.cart.products.filter(
      (item) => item.product.id !== cartItem.product.id
    );
    this.cart.totalAmount -= cartItem.amount;
    this.cart.totalQuantity -= cartItem.quantity;
    if (this.cart.totalQuantity === 0) {
      sessionStorage.removeItem('cart');
    } else {
      this.saveCart();
    }
  }

  decreaseProductQuantity(cartItem: CartItem): void {
    const cartProduct: CartItem | undefined = this.cart.products.find(
      (cartProduct) => cartProduct.product.id === cartItem.product.id
    );
    if (cartProduct) {
      if (cartProduct.quantity === 1) {
        this.removeProduct(cartItem);
      } else {
        cartProduct.quantity--;
        this.cart.totalAmount -= Number(cartItem.product.price);
        --this.cart.totalQuantity;
        this.saveCart();
      }
    }
  }

  saveCart(): void {
    sessionStorage.removeItem('cart');
    sessionStorage.setItem('cart', JSON.stringify(this.cart));
  }

  clearCart(): void {
    sessionStorage.removeItem('cart');
    this.cart.products = [];
    this.cart.totalAmount = 0;
    this.cart.totalQuantity = 0;
  }
}
