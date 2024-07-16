import axios, { AxiosInstance } from 'axios';
import { cookies } from 'next/headers';

const ServerAxios: AxiosInstance = axios.create({
    baseURL: process.env.EXTERNAL_PUBLIC_API_BASE_URL || '',
});

ServerAxios.interceptors.request.use(
    (config) => {
        const token = cookies().get('jwt')?.value;
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default ServerAxios;
