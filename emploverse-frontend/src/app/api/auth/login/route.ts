import { NextRequest, NextResponse } from 'next/server';
import ExternalAuthAPI from '@/services/external/AuthAPI';
import { LoginDTO } from '@/models/AuthDTO';

export async function POST(request: NextRequest) {
    const { email, password, rememberMe }: LoginDTO = await request.json();
    const data = await ExternalAuthAPI.login({ email, password, rememberMe });
    console.log("External login success:", data);
    return NextResponse.json(data);
}
