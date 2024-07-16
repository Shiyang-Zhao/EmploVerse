"use client";
import React, {
  createContext,
  useContext,
  useState,
  ReactNode,
  useEffect,
} from "react";
import { useRouter, usePathname } from "next/navigation";
import cookies from "js-cookie";
import InternalAuthAPI from "@/services/internal/AuthAPI";
import InternalEmployeeAPI from "@/services/internal/EmployeeAPI";
import { UserDTO } from "@/models/UserDTO";
import { SignUpDTO, LoginDTO } from "@/models/AuthDTO";
import { EmployeeDTO } from "@/models/EmployeeDTO";

interface AuthContextType {
  user: UserDTO | null;
  employee: EmployeeDTO | null;
  login: (loginData: LoginDTO) => Promise<void>;
  signup: (signupData: SignUpDTO) => Promise<void>;
  logout: () => void;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

const AuthProvider: React.FC<{ children: ReactNode }> = ({ children }) => {
  const [user, setUser] = useState<UserDTO | null>(null);
  const [employee, setEmployee] = useState<EmployeeDTO | null>(null);
  const router = useRouter();
  const pathname = usePathname();

  useEffect(() => {
    const storedUser = cookies.get("user");
    const storedEmployee = cookies.get("employee");
    const storedJwt = cookies.get("jwt");
    if (storedUser && storedJwt) {
      setUser(JSON.parse(storedUser));
      if (storedEmployee) {
        setEmployee(JSON.parse(storedEmployee));
      }
    }
  }, []);

  const login = async (loginData: LoginDTO) => {
    try {
      const data = await InternalAuthAPI.login(loginData);
      const { jwt, ...userData } = data;
      if (!jwt || !userData) {
        throw new Error("Invalid response from server");
      }
      setUser(userData);
      cookies.set("user", JSON.stringify(userData), { expires: 7 });
      cookies.set("jwt", jwt, { expires: 7 });
      router.push(pathname);
      const employeeData = await InternalEmployeeAPI.getEmployeeById(
        userData.employeeId
      );
      setEmployee(employeeData);
      cookies.set("employee", JSON.stringify(employeeData), { expires: 7 });
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || "Login failed";
      console.log("Login failed:", errorMessage, err);
    }
  };

  const signup = async (signupData: SignUpDTO) => {
    try {
      await InternalAuthAPI.signup(signupData);
      router.push("?modal=login");
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || "Signup failed";
      console.log("Signup failed:", errorMessage, err);
    }
  };

  const logout = () => {
    setUser(null);
    setEmployee(null);
    cookies.remove("user");
    cookies.remove("employee");
    cookies.remove("jwt");
    InternalAuthAPI.logout()
      .then(() => {
        router.push("/?modal=login");
      })
      .catch((err) => {
        const errorMessage = err.response?.data?.message || "Logout failed";
        console.log("Logout failed:", errorMessage, err);
      });
  };

  return (
    <AuthContext.Provider value={{ user, employee, login, signup, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

const useAuth = (): AuthContextType => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error("useAuth must be used within an AuthProvider");
  }
  return context;
};

export { AuthProvider, useAuth };
