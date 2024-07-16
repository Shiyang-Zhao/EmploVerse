import React, { useState } from "react";
import { useAuth } from "@/context/AuthContext";
import { SignUpDTO } from "@/models/AuthDTO";
import handleChange from "@/util/handleChange";

const SignUpForm: React.FC = () => {
  const [formData, setFormData] = useState<SignUpDTO>({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const { signup } = useAuth();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    if (formData.password !== formData.confirmPassword) {
      setError("Passwords do not match");
      return;
    }
    try {
      await signup(formData);
    } catch (err) {
      setError("Sign up failed. Please check your details and try again.");
    }
  };

  return (
    <div className="max-w-md mx-auto p-4">
      <form onSubmit={handleSubmit}>
        {error && <div className="alert alert-error">{error}</div>}
        <div className="form-control mb-2">
          <input
            type="text"
            name="username"
            placeholder="Username"
            className="input input-bordered"
            value={formData.username}
            onChange={(e) => handleChange(e, setFormData)}
            required
          />
        </div>
        <div className="form-control mb-2">
          <input
            type="email"
            name="email"
            placeholder="Email"
            className="input input-bordered"
            value={formData.email}
            onChange={(e) => handleChange(e, setFormData)}
            required
          />
        </div>
        <div className="form-control mb-2">
          <input
            type="password"
            name="password"
            placeholder="Password"
            className="input input-bordered"
            value={formData.password}
            onChange={(e) => handleChange(e, setFormData)}
            required
          />
        </div>
        <div className="form-control mb-2">
          <input
            type="password"
            name="confirmPassword"
            placeholder="Retype Password"
            className="input input-bordered"
            value={formData.confirmPassword}
            onChange={(e) => handleChange(e, setFormData)}
            required
          />
        </div>
        <div className="form-control mt-6">
          <button type="submit" className="btn btn-primary">
            Sign Up
          </button>
        </div>
      </form>
    </div>
  );
};

export default SignUpForm;
