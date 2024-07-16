"use client";
import React, { useState, useEffect } from "react";
import { usePathname, useRouter, useSearchParams } from "next/navigation";
import { useAuth } from "@/context/AuthContext";
import LoginForm from "./LoginForm";
import SignUpForm from "./SignUpForm";

const AuthForm: React.FC = () => {
  const [activeTab, setActiveTab] = useState("login");
  const router = useRouter();
  const searchParams = useSearchParams();
  const pathname = usePathname();
  const { user, logout } = useAuth();

  useEffect(() => {
    const modal = document.getElementById("auth_modal") as HTMLDialogElement;
    const modalType = searchParams.get("modal");

    if (modalType === "login" || modalType === "signup") {
      if (modal) modal.showModal();
      setActiveTab(modalType);
    } else {
      if (modal) modal.close();
    }
  }, [searchParams]);

  const openModal = (type: "login" | "signup") => {
    const modal = document.getElementById("auth_modal") as HTMLDialogElement;
    if (modal) {
      modal.showModal();
      router.push(`?modal=${type}`);
    }
  };

  const closeModal = () => {
    const modal = document.getElementById("auth_modal") as HTMLDialogElement;
    if (modal) {
      modal.close();
      router.push(pathname);
    }
  };

  return (
    <div>
      {user ? (
        <button
          onClick={logout}
          className="hover:text-gray-400 transition-colors duration-300"
        >
          Logout
        </button>
      ) : (
        <button onClick={() => openModal("login")}>Log In</button>
      )}
      <dialog id="auth_modal" className="modal modal-middle sm:modal-middle">
        <div className="modal-box p-0">
          <div role="tablist" className="tabs tabs-bordered tabs-lg">
            <input
              type="radio"
              name="auth_tabs"
              role="tab"
              className="tab ml-24"
              aria-label="Log In"
              checked={activeTab === "login"}
              onChange={() => openModal("login")}
              style={{ outline: "none" }}
            />
            <div role="tabpanel" className="tab-content p-6">
              {activeTab === "login" && <LoginForm />}
            </div>
            <input
              type="radio"
              name="auth_tabs"
              role="tab"
              className="tab ml-24"
              aria-label="Sign Up"
              checked={activeTab === "signup"}
              onChange={() => openModal("signup")}
              style={{ outline: "none" }}
            />
            <div role="tabpanel" className="tab-content p-6">
              {activeTab === "signup" && <SignUpForm />}
            </div>
          </div>
        </div>
        <form method="dialog" className="modal-backdrop" onClick={closeModal}>
          <button type="button">close</button>
        </form>
      </dialog>
    </div>
  );
};

export default AuthForm;
