import { NextRequest, NextResponse } from 'next/server';
import ExternalEmployeeAPI from '@/services/external/EmployeeAPI';
import { EmployeeDTO } from '@/models/EmployeeDTO';

export async function POST(request: NextRequest, context: { params: { id: number } }) {
    const id = context.params.id;
    await ExternalEmployeeAPI.deleteEmployeeById(id);
    return NextResponse.json({ message: `Employee with ID: ${id} successfully deleted.` });
}
