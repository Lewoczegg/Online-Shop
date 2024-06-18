export interface Order {
  id?: number;
  address?: string;
  orderDetails?: OrderDetail[];
}

export interface OrderDetail {
  id?: number;
  name?: string;
  description?: string;
  quantity?: number;
  price?: number;
}
