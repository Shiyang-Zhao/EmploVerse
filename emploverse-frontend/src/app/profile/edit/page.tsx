"use client";
import { useState } from "react";
import Loading from "@/components/layout/Loading";
import { useAuth } from "@/context/AuthContext";
import handleChange from "@/util/handleChange";
import Image from "next/image";
import Link from "next/link";

export default function ProfileEdit() {
  const { user, employee } = useAuth();

  const [formData, setFormData] = useState({
    firstName: employee?.firstName || "",
    lastName: employee?.lastName || "",
    profileImagePath: employee?.profileImagePath || "",
    email: user?.email || "",
    username: user?.username || "",
  });

  const handleSubmit = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 relative">
      <Link
        href="/"
        className="absolute top-4 left-4 text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
      >
        Go back to Home
      </Link>
      <h1 className="text-5xl font-extrabold mb-8 text-gray-200">
        Edit Profile
      </h1>
      <div className="w-full max-w-4xl p-8 bg-gray-800 shadow-xl rounded-lg">
        <form onSubmit={handleSubmit}>
          <div className="flex flex-col lg:flex-row items-center mb-8">
            {formData.profileImagePath && (
              <Image
                src={formData.profileImagePath}
                alt={`${formData.firstName} ${formData.lastName}`}
                width={400}
                height={400}
                className="rounded-full shadow-lg mb-4 lg:mb-0 lg:mr-8"
              />
            )}
            <div className="text-lg leading-relaxed text-gray-300 w-full">
              <div className="mb-4">
                <label className="block mb-1 text-cyan-400">First Name</label>
                <input
                  type="text"
                  name="firstName"
                  value={formData.firstName}
                  onChange={(e) => handleChange(e, setFormData)}
                  className="w-full p-2 rounded bg-gray-700 text-gray-300"
                />
              </div>
              <div className="mb-4">
                <label className="block mb-1 text-cyan-400">Last Name</label>
                <input
                  type="text"
                  name="lastName"
                  value={formData.lastName}
                  onChange={(e) => handleChange(e, setFormData)}
                  className="w-full p-2 rounded bg-gray-700 text-gray-300"
                />
              </div>
              <div className="mb-4">
                <label className="block mb-1 text-cyan-400">Email</label>
                <input
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={(e) => handleChange(e, setFormData)}
                  className="w-full p-2 rounded bg-gray-700 text-gray-300"
                />
              </div>
              <div className="mb-4">
                <label className="block mb-1 text-cyan-400">Username</label>
                <input
                  type="text"
                  name="username"
                  value={formData.username}
                  onChange={(e) => handleChange(e, setFormData)}
                  className="w-full p-2 rounded bg-gray-700 text-gray-300"
                />
              </div>
              <button
                type="submit"
                className="w-full mt-4 p-2 rounded bg-cyan-500 hover:bg-cyan-600 text-gray-900 font-semibold transition-colors duration-300"
              >
                Save Changes
              </button>
            </div>
          </div>
        </form>
      </div>
    </div>
  );
}
