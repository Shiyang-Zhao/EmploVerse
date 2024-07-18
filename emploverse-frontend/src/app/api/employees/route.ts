import { NextRequest, NextResponse } from 'next/server';
import ExternalEmployeeAPI from '@/services/external/EmployeeAPI';

export async function GET(request: NextRequest) {
    const params = request.nextUrl.searchParams;
    const page = parseInt(params.get('page') || '1');
    const size = parseInt(params.get('size') || '10');
    const sortBy = params.get('sortBy') || 'id';
    const sortDir = params.get('sortDir') || 'asc';
    const data = await ExternalEmployeeAPI.getEmployeesBySortPage(page, size, sortBy, sortDir);
    return NextResponse.json(data);
}
