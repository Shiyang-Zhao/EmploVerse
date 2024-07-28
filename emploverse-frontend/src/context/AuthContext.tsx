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

const REMEMBER_ME_COOKIE_EXPIRY_DAYS = parseInt(
  process.env.REMEMBER_ME_COOKIE_EXPIRY_DAYS || "7",
  10
);
const DEFAULT_COOKIE_EXPIRY_DAYS = parseInt(
  process.env.DEFAULT_COOKIE_EXPIRY_DAYS || "1",
  10
);

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
      router.push(pathname);
      const { jwt, ...userData } = data;

      if (!jwt || !userData) {
        throw new Error("Invalid response from server");
      }
      setUser(userData);
      const cookieExpiry = loginData.rememberMe
        ? REMEMBER_ME_COOKIE_EXPIRY_DAYS
        : DEFAULT_COOKIE_EXPIRY_DAYS;
      const cookieOptions = { expires: cookieExpiry };

      cookies.set("user", JSON.stringify(userData), cookieOptions);
      cookies.set("jwt", jwt, cookieOptions);

      if (userData.employeeId) {
        const employeeData = await InternalEmployeeAPI.getEmployeeById(
          userData.employeeId
        );
        setEmployee(employeeData);
        cookies.set("employee", JSON.stringify(employeeData), cookieOptions);
      }
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

  const logout = async () => {
    try {
      setUser(null);
      setEmployee(null);
      cookies.remove("user");
      cookies.remove("employee");
      cookies.remove("jwt");
      await InternalAuthAPI.logout();
      router.push("/?modal=login");
    } catch (err: any) {
      const errorMessage = err.response?.data?.message || "Logout failed";
      console.log("Logout failed:", errorMessage, err);
    }
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
