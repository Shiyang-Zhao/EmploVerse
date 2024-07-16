import { AxiosResponse } from 'axios';
import Axios from '@/util/Axios';
import { UserDTO } from '@/models/UserDTO';

const EXTERNAL_API_BASE_URL = '/api/users';

const ExternalUserAPI = {
    getUserById: async (id: number): Promise<UserDTO> => {
        const response: AxiosResponse<UserDTO> = await Axios.get(`${EXTERNAL_API_BASE_URL}/${id}/get`);
        return response.data;
    },

    getAllUsers: async (): Promise<UserDTO[]> => {
        const response: AxiosResponse<UserDTO[]> = await Axios.get(EXTERNAL_API_BASE_URL);
        return response.data;
    },

    updateUserById: async (id: number, updatedUserDTO: Partial<UserDTO>): Promise<UserDTO> => {
        const response: AxiosResponse<UserDTO> = await Axios.post(`${EXTERNAL_API_BASE_URL}/${id}/update`, updatedUserDTO);
        return response.data;
    },

    deleteUserById: async (id: number): Promise<void> => {
        await Axios.post(`${EXTERNAL_API_BASE_URL}/${id}/delete`);
    }
};

export default ExternalUserAPI;
