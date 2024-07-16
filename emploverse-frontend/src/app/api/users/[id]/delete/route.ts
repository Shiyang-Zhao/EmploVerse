import { NextRequest, NextResponse } from 'next/server';
import ExternalUserAPI from '@/services/external/UserAPI';

export async function POST(request: NextRequest, context: { params: { id: number } }) {
    const id = context.params.id;
    await ExternalUserAPI.deleteUserById(id);
    return NextResponse.json({ message: `User with ID: ${id} successfully deleted.` });
}
