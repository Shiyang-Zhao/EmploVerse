"use client";
import Link from "next/link";
import { useEffect, useState } from "react";
import { EmployeeDTO } from "@/models/EmployeeDTO";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import { useRouter } from "next/navigation";
import UpdateEmployeeForm from "@/components/employee/UpdateEmployeeForm";
import { useAuth } from "@/context/AuthContext";

export default function Employees() {
  const [employees, setEmployees] = useState<EmployeeDTO[]>([]);
  const [selectedEmployeeId, setSelectedEmployeeId] = useState<number | null>(
    null
  );
  const router = useRouter();
  const { user } = useAuth();

  useEffect(() => {
    const getAllEmployees = async () => {
      try {
        const data = await InternalEmployeeAPI.getAllEmployees();
        setEmployees(data);
      } catch (error) {
        console.error("Failed to fetch employees:", error);
      }
    };
    getAllEmployees();
  }, []);

  const handleEmployeeUpdate = (updatedEmployee: EmployeeDTO) => {
    setEmployees((prevEmployees) =>
      prevEmployees.map((employee) =>
        employee.id === updatedEmployee.id ? updatedEmployee : employee
      )
    );
  };

  const handleRowClick = (id: number) => {
    router.push(`/employees/${id}`);
  };

  const handleEditClick = (e: React.MouseEvent, id: number) => {
    e.stopPropagation();
    setSelectedEmployeeId(id);
    const modal = document.getElementById(
      "update_employee_modal"
    ) as HTMLDialogElement;
    if (modal) {
      modal.showModal();
    }
  };

  const handleDeleteClick = async (e: React.MouseEvent, id: number) => {
    e.stopPropagation();
    if (user && user.employeeId === id) {
      alert("You cannot delete your own employee record.");
      return;
    }
    const confirmed = window.confirm(
      "Are you sure you want to delete this employee?"
    );
    if (!confirmed) {
      return;
    }
    try {
      await InternalEmployeeAPI.deleteEmployeeById(id);
      setEmployees((prevEmployees) =>
        prevEmployees.filter((employee) => employee.id !== id)
      );
    } catch (error) {
      alert(`Failed to delete the employee with ID: ${id}. ${error}`);
    }
  };

  return (
    <>
      <div className="w-full max-w-6xl p-8 bg-gray-800 shadow-xl rounded-lg">
        <table className="min-w-full bg-gray-800 text-gray-300">
          <thead>
            <tr className="bg-gray-700">
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                ID
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Name
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Title
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Department
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Action
              </th>
            </tr>
          </thead>
          <tbody>
            {employees.map((employee) => (
              <tr
                key={employee.id}
                className="hover:bg-gray-700 cursor-pointer"
                onClick={() => handleRowClick(employee.id)}
              >
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {employee.id}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {employee.firstName} {employee.lastName}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {employee.firstName}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {employee.firstName}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  <div className="space-x-2">
                    <button
                      className="px-3 py-1 bg-cyan-600 text-gray-200 rounded-lg hover:bg-cyan-500 transition-colors duration-300"
                      onClick={(e) => handleEditClick(e, employee.id)}
                    >
                      Edit
                    </button>
                    <button
                      className="px-3 py-1 bg-red-600 text-gray-200 rounded-lg hover:bg-red-500 transition-colors duration-300"
                      onClick={(e) => handleDeleteClick(e, employee.id)}
                    >
                      Delete
                    </button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
      <UpdateEmployeeForm
        id={selectedEmployeeId}
        onEmployeeUpdate={handleEmployeeUpdate}
      />
    </>
  );
}
