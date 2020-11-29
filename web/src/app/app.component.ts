import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Hospede } from './models/hospede';

import { HospedeService } from './services/hospede.service';
import { CheckInService } from './services/check-in.service';
import { CheckIn } from './models/check-in';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: [
    './styles/banner-container.sass',
    './styles/landing-container.sass',
    './styles/group-section.sass'
  ]
})

export class AppComponent implements OnInit {

  openedAddHospede = false;

  content = '';
  paginaAtual = 0;
  totalElementosPorPagina = 8;
  ehUltimaPagina = true;
  status = "IN";
  hospedes: Hospede[] = [];

  newHospedeForm = new FormGroup({
    nome: new FormControl('', Validators.required),
    documento: new FormControl('', Validators.required),
    telefone: new FormControl('', Validators.required)
  });

  newCheckInForm = new FormGroup({
    dataEntrada: new FormControl(this.nowFormatted(), Validators.required),
    dataSaida: new FormControl(undefined, Validators.required),
    adicionalVeiculo: new FormControl(false),
    documentoHospede: new FormControl('', Validators.required)
  });

  nowFormatted() {
    const date = new Date();
    date.setHours(date.getHours() - 3);
    return date.toISOString().slice(0, 16) + ":00";
  }

  constructor(
    private hospedeService: HospedeService,
    private checkInService: CheckInService
  ) { }

  handleOpenedAddHospede() {
    this.openedAddHospede = !this.openedAddHospede;
  }

  onSelectStatus(newStatus) {
    this.status = newStatus;
    this.loadHospedes();
  }

  onContentChange() {
    this.loadHospedes();
  }

  async handleSubmitNewHospede(event: Event) {
    event.preventDefault();

    if (!this.newHospedeForm.valid) {
      return alert('Erro: Nome, Documento e Telefone são obrigatórios!');
    }

    const [data, status] = await this.hospedeService.store(this.newHospedeForm.value);

    switch (status) {
      case 409:
        alert(data.message);
        break;
      case 201:
        this.newHospedeForm.reset();
        this.newHospedeForm.setValue({ dataEntrada: this.nowFormatted() })
        this.openedAddHospede = false;
        alert('Sucesso, hóspede cadastrado!');
        this.loadHospedes();
        break;
    }
  }

  async handleSubmitNewCheckIn(event: Event) {
    event.preventDefault();

    if (!this.newCheckInForm.valid) {
      return alert('Erro: Data de Entrada, Data de Saída e Documento do Hóspede são obrigatórios!');
    }
    const checkIn = this.newCheckInForm.value as CheckIn;
    const today = new Date();
    const dataEntrada = new Date(checkIn.dataEntrada);
    const dataSaida = new Date(checkIn.dataSaida);
    if (dataEntrada.getTime() >= today.getTime()) {
      return alert('Erro: Data de Entrada não pode estar no futuro.');
    }
    if (dataEntrada.getTime() >= dataSaida.getTime()) {
      return alert('Erro: Data de Entrada não pode ser maior que data de saída.');
    }

    const [data, status] = await this.checkInService.store(this.newCheckInForm.value);

    switch (status) {
      case 409:
        alert(data.message);
        break;
      case 201:
        this.newCheckInForm.reset();
        alert('Sucesso, check in realizado!');
        this.loadHospedes();
        break;
    }
  }

  async ngOnInit() {
    await this.loadHospedes();
  }

  async loadHospedes() {
    const [data] = await this.hospedeService.index(this.paginaAtual, this.totalElementosPorPagina, this.status, this.content);

    this.ehUltimaPagina = data.last;
    this.hospedes = data.content;
  }

  async previousPage() {
    if (this.paginaAtual === 0) return;

    this.paginaAtual--;
    await this.loadHospedes();
  }

  async nextPage() {
    if (this.ehUltimaPagina) return;

    this.paginaAtual++;
    await this.loadHospedes();
  }

}
