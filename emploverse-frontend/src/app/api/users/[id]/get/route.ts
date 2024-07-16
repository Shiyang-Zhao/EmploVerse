import ExternalUserAPI from '@/services/external/UserAPI';
import { NextRequest, NextResponse } from 'next/server';

export async function GET(request: NextRequest, context: { params: { id: number } }) {
    const data = await ExternalUserAPI.getUserById(context.params.id);
    return NextResponse.json(data);
}
