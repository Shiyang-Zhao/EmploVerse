import { NextRequest, NextResponse } from 'next/server';
import ExternalEmployeeAPI from '@/services/external/EmployeeAPI';

export async function GET(request: NextRequest, context: { params: { id: number } }) {
    const data = await ExternalEmployeeAPI.getEmployeeById(context.params.id);
    return NextResponse.json(data);
}
