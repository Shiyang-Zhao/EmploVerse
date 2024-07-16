import axios, { AxiosResponse } from 'axios';
import { SignUpDTO, LoginDTO } from '@/models/AuthDTO';

const INTERNAL_API_BASE_URL = '/api/auth';

const InternalAuthAPI = {
    signup: async (signupData: SignUpDTO): Promise<any> => {
        const response: AxiosResponse = await axios.post(`${INTERNAL_API_BASE_URL}/signup`, signupData);
        return response.data;
    },

    login: async (loginData: LoginDTO): Promise<any> => {
        const response: AxiosResponse = await axios.post(`${INTERNAL_API_BASE_URL}/login`, loginData);
        return response.data;
    },

    logout: async (): Promise<any> => {
        const response: AxiosResponse = await axios.post(`${INTERNAL_API_BASE_URL}/logout`);
        return response.data;
    }
};

export default InternalAuthAPI
