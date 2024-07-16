"use client";
import { useEffect, useState } from "react";
import { UserDTO } from "@/models/UserDTO";
import InternalUserAPI from "@/services/internal/UserAPI";
import Loading from "@/components/layout/Loading";

export default function UserDetail({ params }: { params: { id: number } }) {
  const [user, setUser] = useState<UserDTO | null>(null);
  const id = params.id;

  useEffect(() => {
    const getUser = async () => {
      setUser(id ? await InternalUserAPI.getUserById(id) : null);
    };
    getUser();
  }, [id]);

  if (!user) {
    return <Loading />;
  }

  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-800 via-gray-900 to-black text-gray-300 p-6">
      <div className="bg-gray-900 p-6 rounded-lg shadow-lg">
        <h2 className="text-2xl font-bold mb-4 text-gray-100">User Details</h2>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Username:
          </span>
          <span className="block text-lg text-gray-300">{user.username}</span>
        </div>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Email:
          </span>
          <span className="block text-lg text-gray-300">{user.email}</span>
        </div>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Enabled:
          </span>
          <span className="block text-lg text-gray-300">
            {user.enabled ? "Yes" : "No"}
          </span>
        </div>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Last Login:
          </span>
          <span className="block text-lg text-gray-300">
            {new Date(user.lastLogin).toLocaleString()}
          </span>
        </div>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Employee ID:
          </span>
          <span className="block text-lg text-gray-300">{user.employeeId}</span>
        </div>
        <div className="mb-4">
          <span className="block text-sm font-medium text-gray-500">
            Roles:
          </span>
          <span className="block text-lg text-gray-300">
            {user.roles.join(", ")}
          </span>
        </div>
      </div>
    </div>
  );
}
