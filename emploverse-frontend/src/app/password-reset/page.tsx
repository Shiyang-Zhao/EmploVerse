"use client";
import { useEffect, useState } from "react";
import Link from "next/link";
import { useRouter, useSearchParams } from "next/navigation";
import {
  CompletePasswordResetRequest,
  RequestPasswordResetRequest,
} from "@/models/AuthDTO";
import handleChange from "@/util/handleChange";
import InternalAuthAPI from "@/services/internal/AuthAPI";

export default function PasswordReset() {
  const [requestPasswordResetRequest, setRequestPasswordResetRequest] =
    useState<RequestPasswordResetRequest>({ email: "" });
  const [completePasswordResetRequest, setCompletePasswordResetRequest] =
    useState<CompletePasswordResetRequest>({
      token: "",
      newPassword: "",
      confirmPassword: "",
    });
  const [message, setMessage] = useState<string>("");
  const router = useRouter();
  const searchParams = useSearchParams();
  const token = searchParams.get("token");

  useEffect(() => {
    if (token) {
      setCompletePasswordResetRequest((prev) => ({ ...prev, token }));
    }
  }, [token]);
  const handleRequestPasswordReset = async (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    try {
      await InternalAuthAPI.requestPasswordReset(requestPasswordResetRequest);
      setMessage("A password reset link has been sent to your email address.");
    } catch (error) {
      setMessage("An error occurred. Please try again later.");
    }
  };

  const handleCompletePasswordReset = async (
    event: React.FormEvent<HTMLFormElement>
  ) => {
    event.preventDefault();
    if (
      completePasswordResetRequest.newPassword !==
      completePasswordResetRequest.confirmPassword
    ) {
      setMessage("Passwords do not match.");
      return;
    }
    try {
      await InternalAuthAPI.completePasswordReset(completePasswordResetRequest);
      setMessage("Your password has been successfully reset.");
    } catch (error) {
      setMessage("Failed to reset password. Please try again.");
    }
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
        Password Reset
      </h1>
      <div className="w-full max-w-md p-8 bg-gray-800 shadow-xl rounded-lg">
        {!token ? (
          <form onSubmit={handleRequestPasswordReset} className="space-y-6">
            <div>
              <label
                htmlFor="email"
                className="block text-sm font-medium text-gray-300"
              >
                Email Address
              </label>
              <input
                type="email"
                id="email"
                name="email"
                onChange={(e) =>
                  handleChange(e, setRequestPasswordResetRequest)
                }
                value={requestPasswordResetRequest.email}
                required
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-500 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Send Reset Link
            </button>
          </form>
        ) : (
          <form onSubmit={handleCompletePasswordReset} className="space-y-6">
            <div>
              <label
                htmlFor="newPassword"
                className="block text-sm font-medium text-gray-300"
              >
                New Password
              </label>
              <input
                type="password"
                id="newPassword"
                name="newPassword"
                onChange={(e) =>
                  handleChange(e, setCompletePasswordResetRequest)
                }
                value={completePasswordResetRequest.newPassword}
                required
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-500 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <div>
              <label
                htmlFor="confirmPassword"
                className="block text-sm font-medium text-gray-300"
              >
                Confirm New Password
              </label>
              <input
                type="password"
                id="confirmPassword"
                name="confirmPassword"
                onChange={(e) =>
                  handleChange(e, setCompletePasswordResetRequest)
                }
                value={completePasswordResetRequest.confirmPassword}
                required
                className="appearance-none block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm placeholder-gray-500 focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
              />
            </div>
            <button
              type="submit"
              className="w-full flex justify-center py-2 px-4 border border-transparent rounded-md shadow-sm text-sm font-medium text-white bg-gradient-to-r from-blue-500 to-blue-600 hover:from-blue-600 hover:to-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
            >
              Reset Password
            </button>
          </form>
        )}
        {message && <p className="mt-2 text-sm text-center">{message}</p>}
      </div>
    </div>
  );
}
