import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Hospede } from './models/hospede';

import { HospedeService } from './services/hospede.service';
import { CheckInService } from './services/check-in.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './styles/banner-container.sass',
    './styles/landing-container.sass'
  ]
})

export class AppComponent implements OnInit {

  openedAddHospede = false;

  hospedes: Hospede[] = []

  newHospedeForm = new FormGroup({
    nome: new FormControl('', Validators.required),
    documento: new FormControl('', Validators.required),
    telefone: new FormControl('', Validators.required)
  });

  newCheckInForm = new FormGroup({
    dataEntrada: new FormControl(undefined, Validators.required),
    dataSaida: new FormControl(undefined, Validators.required),
    adicionalVeiculo: new FormControl(false),
    documentoHospede: new FormControl('', Validators.required)
  });

  constructor(
    private hospedeService: HospedeService,
    private checkInService: CheckInService
  ) { }

  handleOpenedAddHospede() {
    this.openedAddHospede = !this.openedAddHospede;
  }

  async handleSubmitNewHospede(event: Event) {
    event.preventDefault();

    const [data, status] = await this.hospedeService.store(this.newHospedeForm.value);

    switch (status) {
      case 409:
        alert(data.message);
        break;
      case 201:
        this.hospedes.push(data);
        this.newHospedeForm.reset();
        this.openedAddHospede = false;
        alert('Sucesso, hóspede cadastrado!');
        break;
    }
  }

  async handleSubmitNewCheckIn(event: Event) {
    event.preventDefault();

    const [data, status] = await this.checkInService.store(this.newCheckInForm.value);

    switch (status) {
      case 409:
        alert(data.message);
        break;
      case 201:
        this.hospedes.push(data);
        this.newCheckInForm.reset();
        alert('Sucesso, check in realizado!');
        break;
    }
  }

  ngOnInit() {
    this.loadHospedes();
  }

  async loadHospedes() {
    this.hospedes = await this.hospedeService.index();
  }

}
