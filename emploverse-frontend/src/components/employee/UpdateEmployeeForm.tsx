"use client";
import React, { useState, useEffect } from "react";
import handleChange from "@/util/handleChange";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import { EmployeeDTO } from "@/models/EmployeeDTO";

const UpdateEmployeeForm: React.FC<{
  id: number | null;
  onEmployeeUpdate: (employee: EmployeeDTO) => void;
}> = ({ id, onEmployeeUpdate }) => {
  const [formData, setFormData] = useState<Partial<EmployeeDTO>>({
    firstName: "",
    lastName: "",
  });

  useEffect(() => {
    if (id) {
      const getEmployee = async () => {
        try {
          const data = await InternalEmployeeAPI.getEmployeeById(id);
          setFormData(() => ({
            firstName: data.firstName,
            lastName: data.lastName,
          }));
        } catch (error) {
          alert("Failed to fetch employee:" + error);
        }
      };
      getEmployee();
    }
  }, [id]);

  const closeModal = () => {
    const modal = document.getElementById(
      "update_employee_modal"
    ) as HTMLDialogElement;
    if (modal) {
      modal.close();
    }
  };

  const handleSubmit = async () => {
    if (id) {
      try {
        const updatedEmployee = await InternalEmployeeAPI.updateEmployeeById(
          id,
          formData
        );
        onEmployeeUpdate(updatedEmployee);
        closeModal();
      } catch (error) {
        alert("Failed to update employee" + error);
      }
    }
  };

  return (
    <dialog
      id="update_employee_modal"
      className="modal modal-middle sm:modal-middle"
    >
      <div className="modal-box p-6 bg-gray-800 text-gray-300 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-4">Update Employee {id}</h2>
        <div className="space-y-4">
          <div>
            <label className="block text-gray-400">First Name</label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={(e) => handleChange(e, setFormData)}
              className="mt-1 p-2 w-full border border-gray-700 rounded-lg bg-gray-900 text-gray-300"
            />
          </div>
          <div>
            <label className="block text-gray-400">Last Name</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={(e) => handleChange(e, setFormData)}
              className="mt-1 p-2 w-full border border-gray-700 rounded-lg bg-gray-900 text-gray-300"
            />
          </div>
        </div>
        <div className="mt-6 flex justify-end space-x-4">
          <button
            onClick={closeModal}
            className="px-4 py-2 bg-gray-500 text-white rounded-lg hover:bg-gray-400 transition-colors duration-300"
          >
            Cancel
          </button>
          <button
            onClick={handleSubmit}
            className="px-4 py-2 bg-blue-500 text-white rounded-lg hover:bg-blue-400 transition-colors duration-300"
          >
            Save
          </button>
        </div>
      </div>
    </dialog>
  );
};

export default UpdateEmployeeForm;
