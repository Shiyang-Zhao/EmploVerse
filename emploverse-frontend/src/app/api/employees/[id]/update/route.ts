import { NextRequest, NextResponse } from 'next/server';
import ExternalEmployeeAPI from '@/services/external/EmployeeAPI';
import { EmployeeDTO } from '@/models/EmployeeDTO';

export async function POST(request: NextRequest, context: { params: { id: number } }) {
    const updatedEmployeeDTO: Partial<EmployeeDTO> = await request.json();
    const data = await ExternalEmployeeAPI.updateEmployeeById(context.params.id, updatedEmployeeDTO);
    return NextResponse.json(data);
}
