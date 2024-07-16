import { NextResponse } from 'next/server';
import ExternalUserAPI from '@/services/external/UserAPI';

export async function GET() {
    const data = await ExternalUserAPI.getAllUsers();
    return NextResponse.json(data);
}
