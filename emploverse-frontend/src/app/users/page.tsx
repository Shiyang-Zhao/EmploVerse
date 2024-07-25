"use client";
import Link from "next/link";
import { useEffect, useRef, useState } from "react";
import { UserDTO } from "@/models/UserDTO";
import InternalUserAPI from "@/services/internal/UserAPI";
import { useRouter } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import Loading from "@/components/layout/Loading";

export default function Users() {
  const [users, setUsers] = useState<Map<number, UserDTO>>(new Map());
  const [pageData, setPageData] = useState({
    page: 1,
    size: 10,
    sortBy: "id",
    sortDir: "asc",
  });
  const [loading, setLoading] = useState(false);
  const [allDataFetched, setAllDataFetched] = useState(false);
  const router = useRouter();
  const { user } = useAuth();
  const observer = useRef<IntersectionObserver>();
  const lastElementRef = useRef<HTMLTableRowElement>(null);

  const getUsers = async () => {
    if (loading || allDataFetched) return;
    setLoading(true);
    try {
      const { content, totalPages } = await InternalUserAPI.getUsersBySortPage(
        pageData.page,
        pageData.size,
        pageData.sortBy,
        pageData.sortDir
      );
      setUsers((prev) => {
        const newUsers = new Map(prev);
        content.forEach((user) => newUsers.set(user.id, user));
        console.log(newUsers);
        return newUsers;
      });
      setAllDataFetched(totalPages <= pageData.page);
    } catch (error) {
      console.error("Failed to fetch users:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    getUsers();
  }, [pageData.page]);

  useEffect(() => {
    if (observer.current) observer.current.disconnect();

    observer.current = new IntersectionObserver(
      (entries) => {
        if (entries[0].isIntersecting && !allDataFetched && !loading) {
          setPageData((prev) => ({ ...prev, page: prev.page + 1 }));
        }
      },
      { root: null, rootMargin: "0px", threshold: 0.1 }
    );

    if (lastElementRef.current)
      observer.current.observe(lastElementRef.current);
    return () => {
      if (observer.current) observer.current.disconnect();
    };
  }, [loading, allDataFetched]);

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
      alert("You cannot delete your own user account.");
      return;
    }
    if (!window.confirm("Are you sure you want to delete this user?")) return;

    try {
      await InternalUserAPI.deleteUserById(id);
      setUsers((prev) => {
        const updatedUsers = new Map(prev);
        updatedUsers.delete(id);
        return updatedUsers;
      });
    } catch (error) {
      alert(`Failed to delete the user with ID: ${id}. ${error}`);
    }
  };

  return (
    <div className="flex flex-col items-center justify-start min-h-screen p-8 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 relative">
      <Link
        href="/"
        className="absolute top-4 left-4 text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
      >
        Go back to Home
      </Link>
      <h1 className="text-5xl font-extrabold my-4 text-gray-200">Users</h1>
      <div className="w-full max-w-6xl p-4 bg-gray-800 shadow-xl rounded-lg">
        <table className="table table-pin-rows min-w-full">
          <thead>
            <tr className="bg-gray-700 text-gray-300 text-base">
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                ID
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                Username
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                Email
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                Last Login
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                Enabled
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-start leading-tight">
                Roles
              </th>
              <th className="py-3 px-4 border-b-2 border-gray-600 text-center leading-tight">
                Action
              </th>
            </tr>
          </thead>
          <tbody>
            {Array.from(users.values()).map((user, index) => (
              <tr
                key={user.id}
                ref={index + 1 === users.size ? lastElementRef : null}
                className="hover:bg-gray-700 cursor-pointer"
                onClick={() => handleRowClick(user.id)}
              >
                <td className="py-3 px-4 border-b border-gray-600 text-start">
                  {user.id}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-start">
                  {user.username}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-start">
                  {user.email}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-start">
                  {user.lastLogin && new Date(user.lastLogin).toLocaleString()}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-start">
                  {user.enabled.toString()}
                </td>
                <td className="py-3 px-4 border-b border-gray-600 text-start">
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
        {loading && !allDataFetched && (
          <div className="h-screen">
            <Loading />
          </div>
        )}
      </div>
    </div>
  );
}
