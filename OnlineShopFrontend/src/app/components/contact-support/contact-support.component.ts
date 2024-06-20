import { Component } from '@angular/core';
import {
  AbstractControl,
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { HeaderComponent } from '../header/header.component';
import { SupportService } from '../../services/support.service';
import { SupportTicket } from '../../models/supportTicket.model';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-contact-support',
  standalone: true,
  imports: [ReactiveFormsModule, HeaderComponent, CommonModule],
  templateUrl: './contact-support.component.html',
  styleUrl: './contact-support.component.css',
})
export class ContactSupportComponent {
  contactForm: FormGroup;
  allertMessage: string = '';
  allertCode: number = 0;

  constructor(private fb: FormBuilder, private supportService: SupportService) {
    this.contactForm = this.fb.group({
      topic: ['', [Validators.required]],
      description: ['', [Validators.required, Validators.maxLength(500)]],
    });
  }

  get topic(): AbstractControl<any, any> | null {
    return this.contactForm.get('topic');
  }

  get description(): AbstractControl<any, any> | null {
    return this.contactForm.get('description');
  }

  onSubmit(): void {
    const ticket: SupportTicket = {
      topic: this.topic?.value,
      description: this.description?.value,
    };

    this.supportService.addTicket(ticket).subscribe({
      next: (result) => {
        this.allertMessage = 'Ticket submitted successfully!';
        this.allertCode = 200;
      },

      error: (error) => {
        this.allertMessage = 'An error occurred while submitting the ticket.';
        this.allertCode = 400;
      },
    });
  }
}
