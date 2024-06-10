export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  mobileNumber?: string;
  password: string;
  authStatus?: string;
}
