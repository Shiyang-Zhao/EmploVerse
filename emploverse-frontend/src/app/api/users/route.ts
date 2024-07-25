import { NextRequest, NextResponse } from 'next/server';
import ExternalUserAPI from '@/services/external/UserAPI';

export async function GET(request: NextRequest) {
    const params = request.nextUrl.searchParams;
    const page = parseInt(params.get('page') || '1');
    const size = parseInt(params.get('size') || '10');
    const sortBy = params.get('sortBy') || 'id';
    const sortDir = params.get('sortDir') || 'asc';
    const data = await ExternalUserAPI.getUsersBySortPage(page, size, sortBy, sortDir);
    return NextResponse.json(data);
}
