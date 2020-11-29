import { Injectable } from '@angular/core';
import { Hospede } from '../models/hospede';

import api from './api.service';

@Injectable()
export class HospedeService {

  constructor() { }

  async store(hospede: Hospede) {
    let response;
    try {
      response = await api.post('/v1/hospedes', hospede);
    } catch (error) {
      response = error.response;
    }
    
    const { data, status } = response;
    
    return [data, status];
  }

  async destroy(id: number) {
    await api.delete(`/v1/hospedes/${id}`);
  }

  async update(id: number, values: Object = {}) {
    const { data, status } = await api.patch(`/v1/hospedes/${id}`, values);

    return [data, status];
  }

  async index(numeroDaPagina, totalDeElementosPorPagina, statusHospede, content) {
    const { data, status } = await api.get(`/v1/check-ins/hospedes?page=${numeroDaPagina}&size=${totalDeElementosPorPagina}&status=${statusHospede}&content=${content.trim()}`);

    return [data, status];
  }

  async show(id: number) {
    const { data, status } = await api.get(`/v1/hospedes/${id}`);

    return [data, status];
  }

}
