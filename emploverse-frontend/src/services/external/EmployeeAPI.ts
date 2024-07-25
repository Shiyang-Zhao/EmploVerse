import { AxiosResponse } from 'axios';
import Axios from '@/util/Axios';
import { EmployeeDTO } from '@/models/EmployeeDTO';

const EXTERNAL_API_BASE_URL = '/api/employees';

const ExternalEmployeeAPI = {
    getEmployeeById: async (id: number): Promise<EmployeeDTO> => {
        const response: AxiosResponse<EmployeeDTO> = await Axios.get(`${EXTERNAL_API_BASE_URL}/${id}/get`);
        return response.data;
    },

    getEmployeesBySortPage: async (page: number, size: number, sortBy: string, sortDir: string): Promise<PageDTO<EmployeeDTO>> => {
        const response: AxiosResponse<PageDTO<EmployeeDTO>> = await Axios.get(`${EXTERNAL_API_BASE_URL}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`);
        return response.data;
    },

    // getAllEmployees: async (): Promise<EmployeeDTO[]> => {
    //     const response: AxiosResponse<EmployeeDTO[]> = await Axios.get(EXTERNAL_API_BASE_URL);
    //     return response.data;
    // },

    updateEmployeeById: async (id: number, updatedEmployeeDTO: Partial<EmployeeDTO>): Promise<EmployeeDTO> => {
        const response: AxiosResponse<EmployeeDTO> = await Axios.post(`${EXTERNAL_API_BASE_URL}/${id}/update`, updatedEmployeeDTO);
        return response.data;
    },

    deleteEmployeeById: async (id: number): Promise<void> => {
        await Axios.post(`${EXTERNAL_API_BASE_URL}/${id}/delete`);
    }
};

export default ExternalEmployeeAPI;
