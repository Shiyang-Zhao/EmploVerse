"use client";
import { useEffect, useState } from "react";
import { EmployeeDTO } from "@/models/EmployeeDTO";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import Calendar from "@/components/layout/Calendar";
import Loading from "@/components/layout/Loading";

export default function EmployeeDetail({ params }: { params: { id: number } }) {
  const [employee, setEmployee] = useState<EmployeeDTO | null>(null);
  const id = params.id;
  useEffect(() => {
    const getEmployee = async () => {
      setEmployee(id ? await InternalEmployeeAPI.getEmployeeById(id) : null);
    };
    getEmployee();
  }, [id]);

  if (!employee) {
    return <Loading />;
  }

  return (
    <>
      <div className="bg-gray-800 shadow overflow-hidden text-gray-300">
        <div className="px-4 py-5 border-b border-gray-600 sm:px-6">
          <h3 className="text-lg leading-6 font-medium text-gray-200">
            Employee ID: {id}
          </h3>
          <p className="mt-1 max-w-2xl text-sm text-gray-500">
            Name: {employee.firstName} {employee.lastName}
          </p>
        </div>
        <div className="border-t border-gray-600">
          <dl>
            <div className="bg-gray-700 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Title</dt>
              <dd className="mt-1 text-sm text-gray-200 sm:mt-0 sm:col-span-2">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book.
              </dd>
            </div>
            <div className="bg-gray-700 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Department</dt>
              <dd className="mt-1 text-sm text-gray-200 sm:mt-0 sm:col-span-2">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book.
              </dd>
            </div>
            <div className="bg-gray-700 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Salary</dt>
              <dd className="mt-1 text-sm text-gray-200 sm:mt-0 sm:col-span-2">
                $Lorem Ipsum is simply dummy text of the printing and
                typesetting industry. Lorem Ipsum has been the industry's
                standard dummy text ever since the 1500s, when an unknown
                printer took a galley of type and scrambled it to make a type
                specimen book. annually
              </dd>
            </div>
            <div className="bg-gray-700 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Projects</dt>
              <dd className="mt-1 text-sm text-gray-200 sm:mt-0 sm:col-span-2">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book.
              </dd>
            </div>
            <div className="bg-gray-700 px-4 py-5 sm:grid sm:grid-cols-3 sm:gap-4 sm:px-6">
              <dt className="text-sm font-medium text-gray-500">Tasks</dt>
              <dd className="mt-1 text-sm text-gray-200 sm:mt-0 sm:col-span-2">
                Lorem Ipsum is simply dummy text of the printing and typesetting
                industry. Lorem Ipsum has been the industry's standard dummy
                text ever since the 1500s, when an unknown printer took a galley
                of type and scrambled it to make a type specimen book.
              </dd>
            </div>
          </dl>
        </div>
      </div>
      <Calendar employeeId={id} />
    </>
  );
}
