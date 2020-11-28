export class Hospede {

  id?: number;
  nome: string = '';
  documento: string = '';
  telefone: string = '';

  constructor(values: Object = {}) {
    Object.assign(this, values);
  }
  
}
