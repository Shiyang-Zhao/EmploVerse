"use client";
import React from "react";
import Image from "next/image";
import Link from "next/link";
import { useAuth } from "@/context/AuthContext";
import AuthForm from "../auth/AuthForm";

export default function Header() {
  const { user } = useAuth();

  return (
    <div className="flex flex-col items-center p-6 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 fixed left-0 top-0 h-full shadow-xl">
      <div className="mb-6">
        <Image
          src="/title.svg"
          alt="title"
          height={0}
          width={0}
          style={{ width: "200px", height: "auto" }}
          priority
        />
      </div>
      <nav>
        <ul className="list-none space-y-4 text-lg font-medium">
          <li>
            <Link
              href="/"
              className="hover:text-cyan-400 transition-colors duration-300"
            >
              Home
            </Link>
          </li>
          <li>
            <Link
              href="/about"
              className="hover:text-cyan-400 transition-colors duration-300"
            >
              About
            </Link>
          </li>
          <li>
            <Link
              href="/contact"
              className="hover:text-cyan-400 transition-colors duration-300"
            >
              Contact
            </Link>
          </li>
          {user && (
            <>
              <li>
                <Link
                  href="/employees"
                  className="hover:text-cyan-400 transition-colors duration-300"
                >
                  Employees
                </Link>
              </li>
              <li>
                <Link
                  href="/users"
                  className="hover:text-cyan-400 transition-colors duration-300"
                >
                  Users
                </Link>
              </li>
              <li>
                <Link
                  href="/profile"
                  className="hover:text-cyan-400 transition-colors duration-300"
                >
                  Profile
                </Link>
              </li>
            </>
          )}
          <li>
            <AuthForm />
          </li>
        </ul>
      </nav>
    </div>
  );
}
