"use client";
import { useEffect, useState } from "react";
import { EmployeeDTO } from "@/models/EmployeeDTO";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import Calendar from "@/components/layout/Calendar";
import Loading from "@/components/layout/Loading";
import UpdateEmployeeForm from "@/components/employee/UpdateEmployeeForm";

export default function EmployeeDetail({ params }: { params: { id: number } }) {
  const [employee, setEmployee] = useState<EmployeeDTO | null>(null);
  const id = params.id;
  useEffect(() => {
    const getEmployee = async () => {
      setEmployee(id ? await InternalEmployeeAPI.getEmployeeById(id) : null);
    };
    getEmployee();
  }, [id]);

  const handleEmployeeUpdate = (updatedEmployee: EmployeeDTO) => {
    setEmployee(updatedEmployee);
  };

  const handleEditClick = (e: React.MouseEvent, id: number) => {
    e.stopPropagation();
    const modal = document.getElementById(
      "update_employee_modal"
    ) as HTMLDialogElement;
    if (modal) {
      modal.showModal();
    }
  };

  if (!employee) {
    return (
      <div className="h-screen">
        <Loading />
      </div>
    );
  }

  return (
    <>
      <div className="bg-gray-800 shadow text-gray-300">
        <div className="flex justify-between px-4 py-5 border-b border-gray-600 sm:px-6">
          <div>
            <h3 className="text-lg leading-6 font-medium text-gray-200">
              Employee ID: {id}
            </h3>
            <p className="mt-1 max-w-2xl text-sm text-gray-500">
              Name: {employee.firstName} {employee.lastName}
            </p>
          </div>
          <div>
            <button
              className="text-sm bg-gray-700 px-4 py-2 rounded hover:bg-gray-600 transition-colors duration-300"
              onClick={(e) => handleEditClick(e, id)}
            >
              Edit Employee
            </button>
          </div>
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
      <UpdateEmployeeForm id={id} onEmployeeUpdate={handleEmployeeUpdate} />
    </>
  );
}
