"use client";
import Link from "next/link";
import { useEffect, useState } from "react";
import { UserDTO } from "@/models/UserDTO";
import InternalUserAPI from "@/services/internal/UserAPI";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";

export default function Users() {
  const [users, setUsers] = useState<UserDTO[]>([]);
  const router = useRouter();
  const { user } = useAuth();

  useEffect(() => {
    const getAllUsers = async () => {
      try {
        const data = await InternalUserAPI.getAllUsers();
        setUsers(data);
      } catch (error) {
        console.error("Failed to fetch users:", error);
      }
    };
    getAllUsers();
  }, []);

  const handleRowClick = (id: number) => {
    router.push(`/users/${id}`);
  };

  const handleEditClick = (e: React.MouseEvent, id: number) => {
    e.stopPropagation();
    router.push(`/users/${id}/edit`);
  };

  const handleDeleteClick = async (e: React.MouseEvent, id: number) => {
    e.stopPropagation();
    if (user && user.id === id) {
      alert("You cannot delete your user account.");
      return;
    }
    const confirmed = window.confirm(
      "Are you sure you want to delete this user?"
    );
    if (!confirmed) {
      return;
    }
    try {
      await InternalUserAPI.deleteUserById(id);
      setUsers((prevUsers) => prevUsers.filter((user) => user.id !== id));
    } catch (error) {
      alert(`Failed to delete the user with ID: ${id}. ${error}`);
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
                Username
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Email
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Last Login
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Enabled
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Roles
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Action
              </th>
            </tr>
          </thead>
          <tbody>
            {users.map((user) => (
              <tr
                key={user.id}
                className="hover:bg-gray-700 cursor-pointer"
                onClick={() => handleRowClick(user.id)}
              >
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {user.id}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {user.username}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {user.email}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {new Date(user.lastLogin).toLocaleString()}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {user.enabled.toString()}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  {user.roles
                    .map((role) => role.toString().replace("ROLE_", ""))
                    .join(", ")}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-center">
                  <div className="space-x-2">
                    <button
                      className="px-3 py-1 bg-cyan-600 text-gray-200 rounded-lg hover:bg-cyan-500 transition-colors duration-300"
                      onClick={(e) => handleEditClick(e, user.id)}
                    >
                      Edit
                    </button>
                    <button
                      className="px-3 py-1 bg-red-600 text-gray-200 rounded-lg hover:bg-red-500 transition-colors duration-300"
                      onClick={(e) => handleDeleteClick(e, user.id)}
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
    </>
  );
}
