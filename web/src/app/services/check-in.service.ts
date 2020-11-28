import { Injectable } from '@angular/core';

import { CheckIn } from '../models/check-in';
import api from './api.service';

@Injectable()
export class CheckInService {

  constructor() { }

  async store(checkIn: CheckIn) {
    let response;
    try {
      response = await api.post('/v1/check-ins', checkIn);
    } catch (error) {
      response = error.response;
    }
    
    const { data, status } = response;
    
    return [data, status];
  }

}
