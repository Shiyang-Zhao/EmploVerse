"use client";
import { useEffect, useState } from "react";
import InternalUserAPI from "@/services/internal/UserAPI";
import Loading from "@/components/layout/Loading";
import { UserDTO } from "@/models/UserDTO";
import { EmployeeDTO } from "@/models/EmployeeDTO";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import { useRouter } from "next/navigation";
import handleChange from "@/util/handleChange";

export default function UserEdit({ params }: { params: { id: number } }) {
  const [user, setUser] = useState<Partial<UserDTO>>({});
  const [employee, setEmployee] = useState<Partial<EmployeeDTO>>({});
  const router = useRouter();
  const id = params.id;

  const getUserAndEmployee = async () => {
    try {
      const userData = await InternalUserAPI.getUserById(id);
      setUser(userData);

      if (userData.employeeId) {
        const employeeData = await InternalEmployeeAPI.getEmployeeById(
          userData.employeeId
        );
        setEmployee(employeeData);
      }
    } catch (error) {
      console.error("Failed to fetch data:", error);
    }
  };

  const handleAuthInfoSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await InternalUserAPI.updateUserById(id, user);
      router.back();
    } catch (error) {
      console.error("Failed to update user:", error);
    }
  };

  const handleBasicInfoSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    if (employee && user?.employeeId) {
      await InternalEmployeeAPI.updateEmployeeById(user.employeeId, employee);
      router.back();
    }
  };

  useEffect(() => {
    getUserAndEmployee();
  }, [id]);

  if (Object.keys(user).length === 0 || Object.keys(employee).length === 0) {
    return (
      <div className="h-screen">
        <Loading />
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-800 via-gray-900 to-black text-gray-300 p-6">
      <div className="bg-gray-900 p-6 rounded-lg shadow-lg mb-6">
        <h2 className="text-2xl font-bold mb-4 text-gray-100">Account</h2>
        <form onSubmit={handleAuthInfoSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-500">
              Username
            </label>
            <input
              type="text"
              name="username"
              className="block w-full mt-1 p-2 bg-gray-800 text-gray-300 rounded"
              value={user.username}
              onChange={(e) => handleChange(e, setUser)}
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-500">
              Email
            </label>
            <input
              type="email"
              name="email"
              className="block w-full mt-1 p-2 bg-gray-800 text-gray-300 rounded"
              value={user.email}
              onChange={(e) => handleChange(e, setUser)}
            />
          </div>
          <button
            type="submit"
            className="px-4 py-2 bg-cyan-600 hover:bg-cyan-500 rounded text-gray-100"
          >
            Save
          </button>
        </form>
      </div>

      <div className="bg-gray-900 p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-4 text-gray-100">Basic</h2>
        <form onSubmit={handleBasicInfoSubmit}>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-500">
              First Name
            </label>
            <input
              type="text"
              name="firstName"
              className="block w-full mt-1 p-2 bg-gray-800 text-gray-300 rounded"
              value={employee.firstName}
              onChange={(e) => handleChange(e, setEmployee)}
            />
          </div>
          <div className="mb-4">
            <label className="block text-sm font-medium text-gray-500">
              Last Name
            </label>
            <input
              type="text"
              name="lastName"
              className="block w-full mt-1 p-2 bg-gray-800 text-gray-300 rounded"
              value={employee.lastName}
              onChange={(e) => handleChange(e, setEmployee)}
            />
          </div>
          <button
            type="submit"
            className="px-4 py-2 bg-cyan-600 hover:bg-cyan-500 rounded text-gray-100"
          >
            Save
          </button>
        </form>
      </div>
    </div>
  );
}
