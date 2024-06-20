import { User } from './user.model';

export interface SupportTicket {
  topic?: String;
  description?: String;
  user?: User;
}
