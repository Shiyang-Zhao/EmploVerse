import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import Header from "@/components/layout/Header";
import { Heads, Scripts } from "./preload";
import { AuthProvider } from "@/context/AuthContext";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "EmploVerse",
  description: "Employee Management System",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <Heads />
      <body className={inter.className}>
        <AuthProvider>
          <div className="flex h-screen">
            <div className="w-60 shadow-lg bg-gradient-to-t from-white via-white">
              <Header />
            </div>
            <div className="flex-1 overflow-auto">{children}</div>
          </div>
        </AuthProvider>
        <Scripts />
      </body>
    </html>
  );
}
