import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import { HospedeService } from './services/hospede.service';
import { CheckInService } from './services/check-in.service';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [AppComponent],
  imports: [BrowserModule, FormsModule, ReactiveFormsModule],
  providers: [HospedeService, CheckInService],
  bootstrap: [AppComponent]
})

export class AppModule { }
