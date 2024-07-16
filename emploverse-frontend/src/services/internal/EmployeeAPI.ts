import axios, { AxiosResponse } from 'axios';
import { EmployeeDTO } from '@/models/EmployeeDTO';

const INTERNAL_API_BASE_URL = '/api/employees';

const InternalEmployeeAPI = {
    getEmployeeById: async (id: number): Promise<EmployeeDTO> => {
        const response: AxiosResponse<EmployeeDTO> = await axios.get(`${INTERNAL_API_BASE_URL}/${id}/get`);
        return response.data;
    },

    getAllEmployees: async (): Promise<EmployeeDTO[]> => {
        const response: AxiosResponse<EmployeeDTO[]> = await axios.get(INTERNAL_API_BASE_URL);
        return response.data;
    },

    updateEmployeeById: async (id: number, updatedEmployeeDTO: Partial<EmployeeDTO>): Promise<EmployeeDTO> => {
        const response: AxiosResponse<EmployeeDTO> = await axios.post(`${INTERNAL_API_BASE_URL}/${id}/update`, updatedEmployeeDTO);
        return response.data;
    },

    deleteEmployeeById: async (id: number): Promise<void> => {
        await axios.post(`${INTERNAL_API_BASE_URL}/${id}/delete`);
    }
};

export default InternalEmployeeAPI
