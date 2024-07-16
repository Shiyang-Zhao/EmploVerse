import Link from "next/link";

export default function EmployeeDetailLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300">
      <div className="bg-gray-800 text-gray-300 p-4 shadow-md">
        <div className="container mx-auto flex justify-between items-center">
          <Link
            href="/employees"
            className="text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
          >
            Back to List
          </Link>
          <h1 className="text-xl font-bold">Employee Details</h1>
        </div>
      </div>
      <main>
        <div className="container mx-auto">{children}</div>
      </main>
    </div>
  );
}
