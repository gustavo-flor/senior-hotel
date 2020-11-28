import axios from 'axios';

import { environment } from '../../environments/environment';

const api = axios.create({
  baseURL: environment.apiEndpoint
});

export default api;