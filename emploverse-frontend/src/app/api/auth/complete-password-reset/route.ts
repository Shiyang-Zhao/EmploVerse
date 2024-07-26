import { NextRequest, NextResponse } from 'next/server';
import ExternalAuthAPI from '@/services/external/AuthAPI';
import { CompletePasswordResetRequest, LoginDTO, RequestPasswordResetRequest } from '@/models/AuthDTO';

export async function POST(request: NextRequest) {
    const { token, newPassword, confirmPassword }: CompletePasswordResetRequest = await request.json();
    const data = await ExternalAuthAPI.completePasswordReset({ token, newPassword, confirmPassword });
    return NextResponse.json(data);
}
