import { NextRequest, NextResponse } from 'next/server';
import ExternalAuthAPI from '@/services/external/AuthAPI';
import { SignUpDTO } from '@/models/AuthDTO';

export async function POST(request: NextRequest) {
    const { username, email, password, confirmPassword }: SignUpDTO = await request.json();
    const data = await ExternalAuthAPI.signup({ username, email, password, confirmPassword });
    console.log("External signup success:", data);
    return NextResponse.json(data);
}
