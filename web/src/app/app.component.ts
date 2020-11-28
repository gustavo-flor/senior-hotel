import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Hospede } from './models/hospede';

import { HospedeService } from './services/hospede.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './styles/banner-container.sass',
    './styles/landing-container.sass'
  ]
})

export class AppComponent implements OnInit {

  openedAddHospede = true;
  hospedes: Hospede[] = []
  newHospedeForm = new FormGroup({
    nome: new FormControl('', Validators.required),
    documento: new FormControl('', Validators.required),
    telefone: new FormControl('', Validators.required)
  });

  constructor(private hospedeService: HospedeService) { }

  handleOpenedAddHospede() {
    this.openedAddHospede = !this.openedAddHospede;
  }

  onSubmit(event: Event) {
    event.preventDefault();

    this.hospedeService.store({ ...this.newHospedeForm.value, id: null});
    console.log(this.hospedes);
    alert('Sucesso, hóspede cadastrado!')
  }

  ngOnInit() {
    this.hospedes = this.hospedeService.index();
  }

}
