import axios, { AxiosInstance } from 'axios';
import cookies from 'js-cookie';
const ClientAxios: AxiosInstance = axios.create({
    baseURL: process.env.EXTERNAL_PUBLIC_API_BASE_URL || '',
});

ClientAxios.interceptors.request.use(
    (config) => {
        const token = cookies.get('jwt');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default ClientAxios;
