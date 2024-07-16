import React, { useState } from "react";
import Image from "next/image";
import { useAuth } from "@/context/AuthContext";
import { LoginDTO } from "@/models/AuthDTO";
import handleChange from "@/util/handleChange";

const LoginForm: React.FC = () => {
  const [formData, setFormData] = useState<LoginDTO>({
    email: "",
    password: "",
  });
  const [error, setError] = useState("");
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError("");
    try {
      await login(formData);
    } catch (err) {
      setError("Login failed. Please check your credentials and try again.");
    }
  };

  return (
    <div className="max-w-md mx-auto p-4">
      <form onSubmit={handleSubmit}>
        {error && <div className="alert alert-error">{error}</div>}
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
        <div className="form-control mt-6">
          <button type="submit" className="btn btn-primary">
            Log In
          </button>
        </div>
      </form>
      <div className="divider">OR</div>
      <div className="flex flex-col space-y-2">
        <button
          type="button"
          className="btn btn-outline flex items-center justify-center"
          // onClick={() => signIn('google')}
        >
          <Image
            src="/Google.svg"
            alt="Google Logo"
            className="w-6 h-6 mr-2"
            width={35}
            height={35}
          />
          Log in with Google
        </button>
        <button
          type="button"
          className="btn btn-outline flex items-center justify-center"
          // onClick={() => signIn('microsoft')}
        >
          <Image
            src="/Microsoft.svg"
            alt="Microsoft Logo"
            className="w-6 h-6 mr-2"
            width={35}
            height={35}
          />
          Log in with Microsoft
        </button>
        <button
          type="button"
          className="btn btn-outline flex items-center justify-center"
          // onClick={() => signIn('github')}
        >
          <Image
            src="/Github.svg"
            alt="GitHub Logo"
            className="w-6 h-6 mr-2"
            width={35}
            height={35}
          />
          Log in with GitHub
        </button>
      </div>
    </div>
  );
};

export default LoginForm;
