import Link from "next/link";

export default function UserDetailLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <div className="min-h-screen bg-gradient-to-b from-gray-800 via-gray-900 to-black text-gray-300">
      <div className="bg-black text-gray-300 p-5 shadow-lg">
        <div className="container mx-auto flex justify-between items-center">
          <Link
            href="/users"
            className="text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
          >
            Back to Users
          </Link>
          <h1 className="text-2xl font-bold">User Details</h1>
          <div>
            <button className="text-sm bg-gray-700 px-4 py-2 rounded hover:bg-gray-600 transition-colors duration-300">
              Edit User
            </button>
          </div>
        </div>
      </div>
      <main>
        <div className="container mx-auto p-4">
          <div className="bg-gray-800 rounded-lg shadow-xl p-6">{children}</div>
        </div>
      </main>
    </div>
  );
}
