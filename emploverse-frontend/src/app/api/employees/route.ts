import { NextRequest, NextResponse } from 'next/server';
import ExternalEmployeeAPI from '@/services/external/EmployeeAPI';

export async function GET() {
    const data = await ExternalEmployeeAPI.getAllEmployees();
    return NextResponse.json(data);
}
