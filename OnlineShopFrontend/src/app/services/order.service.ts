import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Order } from '../models/order.model';
import { AppConstants } from '../components/constants/app.constants';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private http: HttpClient) {}

  placeOrder(order: Order) {
    const url = 'http://localhost:8080/' + AppConstants.PLACE_ORDER_API_URL;
    return this.http.post(url, order);
  }

  getOrders(): Observable<Order[]> {
    const url = 'http://localhost:8080/' + AppConstants.GET_ORDERS_API_URL;
    return this.http.get<Order[]>(url);
  }
}
