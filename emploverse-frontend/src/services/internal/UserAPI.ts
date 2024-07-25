import axios, { AxiosResponse } from 'axios';
import { UserDTO } from '@/models/UserDTO';

const INTERNAL_API_BASE_URL = '/api/users';

const InternalUserAPI = {
    getUserById: async (id: number): Promise<UserDTO> => {
        const response: AxiosResponse<UserDTO> = await axios.get(`${INTERNAL_API_BASE_URL}/${id}/get`);
        return response.data;
    },

    getUsersBySortPage: async (page: number, size: number, sortBy: string, sortDir: string): Promise<PageDTO<UserDTO>> => {
        const response: AxiosResponse<PageDTO<UserDTO>> = await axios.get(`${INTERNAL_API_BASE_URL}?page=${page}&size=${size}&sortBy=${sortBy}&sortDir=${sortDir}`);
        return response.data;
    },

    // getAllUsers: async (): Promise<UserDTO[]> => {
    //     const response: AxiosResponse<UserDTO[]> = await axios.get(INTERNAL_API_BASE_URL);
    //     return response.data;
    // },

    updateUserById: async (id: number, updatedUserDTO: Partial<UserDTO>): Promise<UserDTO> => {
        const response: AxiosResponse<UserDTO> = await axios.post(`${INTERNAL_API_BASE_URL}/${id}/update`, updatedUserDTO);
        return response.data;
    },

    deleteUserById: async (id: number): Promise<void> => {
        await axios.post(`${INTERNAL_API_BASE_URL}/${id}/delete`);
    }
};

export default InternalUserAPI