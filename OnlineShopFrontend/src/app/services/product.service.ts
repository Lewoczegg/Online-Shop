import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Product } from '../models/product.model';
import { AppConstants } from '../components/constants/app.constants';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private http: HttpClient) {}

  getAllProducts(): Observable<Product[]> {
    const url =
      'http://localhost:8080/' + AppConstants.GET_ALL_PRODUCTS_API_URL;
    return this.http.get<Product[]>(url);
  }

  getMyProducts(): Observable<Product[]> {
    const url = 'http://localhost:8080/' + AppConstants.GET_MY_PRODUCTS_API_URL;
    return this.http.get<Product[]>(url);
  }

  getProductById(productId: number): Observable<Product> {
    const url =
      'http://localhost:8080/' +
      AppConstants.GET_PRODUCT_BY_ID_API_URL +
      '?productId=' +
      productId;
    return this.http.get<Product>(url);
  }

  addProduct(product: Product) {
    const url = 'http://localhost:8080/' + AppConstants.ADD_PRODUCT_API_URL;
    return this.http.post<Product>(url, product);
  }

  updateProduct(product: Product) {
    const url = 'http://localhost:8080/' + AppConstants.UPDATE_PRODUCT_API_URL;
    return this.http.put<Product>(url, product);
  }

  deleteProduct(productId: number) {
    const url =
      'http://localhost:8080/' +
      AppConstants.DELETE_PRODUCT_API_URL +
      '?productId=' +
      productId;
    return this.http.delete(url);
  }
}
