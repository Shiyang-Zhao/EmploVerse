import { AxiosInstance } from 'axios';

let Axios: AxiosInstance;

if (typeof window === 'undefined') {
    Axios = require('./ServerAxios').default;
} else {
    Axios = require('./ClientAxios').default;
}

export default Axios;


// import axios, { AxiosInstance, AxiosRequestConfig, AxiosRequestHeaders, AxiosResponse, AxiosStatic } from 'axios';

// const createAxiosInstance = async (): Promise<AxiosInstance> => {
//     const jwt = typeof window === 'undefined'
//         ? (await import('next/headers')).cookies().get('jwt')?.value
//         : (await import('js-cookie')).default.get('jwt');

//     const axiosInstance: AxiosInstance = axios.create({
//         baseURL: process.env.EXTERNAL_PUBLIC_API_BASE_URL || '',
//     });

//     axiosInstance.interceptors.request.use(
//         (config) => {
//             if (jwt) {
//                 if (!config.headers) {
//                     config.headers = {} as AxiosRequestHeaders;
//                 }
//                 config.headers.Authorization = `Bearer ${jwt}`;
//             }
//             return config;
//         },
//         (error) => {
//             return Promise.reject(error);
//         }
//     );

//     return axiosInstance;
// };

// const Axios = {
//     get: async (url: string, config: AxiosRequestConfig = {}): Promise<AxiosResponse> => {
//         const axiosInstance = await createAxiosInstance();
//         return axiosInstance.get(url, config);
//     },

//     post: async (url: string, data?: any, config: AxiosRequestConfig = {}): Promise<AxiosResponse> => {
//         const axiosInstance = await createAxiosInstance();
//         return axiosInstance.post(url, data, config);
//     },
// };

// export default Axios;

