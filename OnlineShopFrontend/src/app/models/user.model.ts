export interface User {
  id?: number;
  firstName: string;
  lastName: string;
  email: string;
  mobileNumber?: string;
  password: string;
  authStatus?: string;
  iban?: number;
  address?: string;
  dateOfBirth?: Date
}
