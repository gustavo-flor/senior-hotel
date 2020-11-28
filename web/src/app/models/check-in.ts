import { Hospede } from './hospede';

export class CheckIn {

  id?: number;
  dataEntrada: Date;
  dataSaida: Date;
  hospede: Hospede;
  veiculoAdicional: boolean = false;

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
  
}
