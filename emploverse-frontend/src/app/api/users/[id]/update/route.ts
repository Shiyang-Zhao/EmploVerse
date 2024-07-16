import { UserDTO } from '@/models/UserDTO';
import ExternalUserAPI from '@/services/external/UserAPI';
import { NextRequest, NextResponse } from 'next/server';

export async function POST(request: NextRequest, context: { params: { id: number } }) {
    const updatedUserDTO: Partial<UserDTO> = await request.json();
    const data = await ExternalUserAPI.updateUserById(context.params.id, updatedUserDTO);
    return NextResponse.json(data);
}
