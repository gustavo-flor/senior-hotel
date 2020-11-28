import { Injectable } from '@angular/core';
import { Hospede } from '../models/hospede';

@Injectable()
export class HospedeService {

  sequence: number = 0;
  hospedes: Hospede[] = [];

  constructor() { }

  store(hospede: Hospede) {
    if (!hospede.id) { 
      hospede.id = ++this.sequence;
    }
    
    this.index().push(hospede);
    return hospede;
  }

  destroy(id: number) {
    this.hospedes = this.index().filter(hospede => hospede.id !== id);
  }

  update(id: number, values: Object = {}) {
    let hospede = this.show(id);

    if (!hospede)
      return null;
  
    Object.assign(hospede, values);
    return hospede;
  }

  index() {
    return this.hospedes;
  }

  show(id: number) {
    return this.index()
      .filter(hospede => hospede.id === id)
      .pop();
  }

}
