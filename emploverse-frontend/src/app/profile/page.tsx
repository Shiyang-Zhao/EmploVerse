"use client";
import Loading from "@/components/layout/Loading";
import { useAuth } from "@/context/AuthContext";
import Image from "next/image";
import Link from "next/link";

export default function Profile() {
  const { user, employee } = useAuth();

  if (!user || !employee) {
    return <Loading />;
  }

  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 relative">
      <Link
        href="/"
        className="absolute top-4 left-4 text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
      >
        Go back to Home
      </Link>
      <h1 className="text-5xl font-extrabold mb-8 text-gray-200">Profile</h1>
      <div className="w-full max-w-4xl p-8 bg-gray-800 shadow-xl rounded-lg">
        <div className="flex flex-col lg:flex-row items-center mb-8">
          {employee.profileImagePath && (
            <Image
              src={employee.profileImagePath}
              alt={`${employee.firstName} ${employee.lastName}`}
              width={400}
              height={400}
              className="rounded-full shadow-lg mb-4 lg:mb-0 lg:mr-8"
            />
          )}
          <div className="text-lg leading-relaxed text-gray-300">
            <h2 className="text-3xl font-bold mb-4 text-cyan-400">
              {employee.firstName} {employee.lastName}
            </h2>
            <p>
              Hi, I'm {employee.firstName} {employee.lastName}, a passionate
              developer with expertise in web development. I love creating
              beautiful and functional websites using modern technologies. My
              goal is to constantly improve my skills and stay up-to-date with
              the latest trends in the industry. When I'm not coding, you can
              find me exploring new places, reading tech blogs, or spending time
              with my family.
            </p>
            <p className="mt-4">
              Skills: JavaScript, React, Next.js, HTML, CSS, Tailwind CSS,
              Node.js
            </p>
            <p className="mt-4">
              <strong>ID:</strong> {user.id}
            </p>
            <p className="mt-2">
              <strong>Username:</strong> {user.username}
            </p>
            <p className="mt-2">
              <strong>Email:</strong> {user.email}
            </p>
            <p className="mt-2">
              <strong>Last Login:</strong>{" "}
              {user.lastLogin && new Date(user.lastLogin).toLocaleString()}
            </p>
            <p className="mt-2">
              <strong>Roles:</strong> {user.roles.join(", ")}
            </p>
            <p className="mt-2">
              <strong>Employee ID:</strong> {user.employeeId}
            </p>
          </div>
        </div>
        <Link href={`/users/${user.id}/edit`} className="btn btn-info">
          Edit
        </Link>
      </div>
    </div>
  );
}
