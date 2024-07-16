import { NextRequest, NextResponse } from 'next/server';
import ExternalAuthAPI from '@/services/external/AuthAPI';

export async function POST(request: NextRequest) {
    const data = await ExternalAuthAPI.logout();
    return NextResponse.json(data);
}
