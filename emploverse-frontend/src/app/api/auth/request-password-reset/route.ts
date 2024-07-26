import { NextRequest, NextResponse } from 'next/server';
import ExternalAuthAPI from '@/services/external/AuthAPI';
import { LoginDTO, RequestPasswordResetRequest } from '@/models/AuthDTO';

export async function POST(request: NextRequest) {
    const { email }: RequestPasswordResetRequest = await request.json();
    const data = await ExternalAuthAPI.requestPasswordReset({ email });
    return NextResponse.json(data);
}
