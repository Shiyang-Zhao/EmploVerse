import Link from "next/link";

export default function Contact() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 relative">
      <Link
        href="/"
        className="absolute top-4 left-4 text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
      >
        Go back to Home
      </Link>
      <h1 className="text-5xl font-extrabold mb-8 text-gray-200">Contact Us</h1>
      <div className="w-full max-w-4xl p-8 bg-gray-800 shadow-xl rounded-lg">
        <form className="space-y-6">
          <div>
            <label
              htmlFor="name"
              className="block text-sm font-medium text-gray-300"
            >
              Name
            </label>
            <input
              type="text"
              id="name"
              className="mt-1 p-3 w-full border border-gray-600 rounded-lg shadow-sm focus:ring focus:ring-cyan-200 focus:border-cyan-500 bg-gray-700 text-gray-200"
              required
            />
          </div>
          <div>
            <label
              htmlFor="email"
              className="block text-sm font-medium text-gray-300"
            >
              Email
            </label>
            <input
              type="email"
              id="email"
              className="mt-1 p-3 w-full border border-gray-600 rounded-lg shadow-sm focus:ring focus:ring-cyan-200 focus:border-cyan-500 bg-gray-700 text-gray-200"
              required
            />
          </div>
          <div>
            <label
              htmlFor="message"
              className="block text-sm font-medium text-gray-300"
            >
              Message
            </label>
            <textarea
              id="message"
              rows={4}
              className="mt-1 p-3 w-full border border-gray-600 rounded-lg shadow-sm focus:ring focus:ring-cyan-200 focus:border-cyan-500 bg-gray-700 text-gray-200"
              required
            ></textarea>
          </div>
          <div className="text-center">
            <button
              type="submit"
              className="inline-block px-6 py-3 text-white bg-cyan-600 rounded-lg shadow hover:bg-cyan-500 transition-colors duration-300"
            >
              Send Message
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
