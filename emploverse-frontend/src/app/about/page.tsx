import Image from "next/image";
import Link from "next/link";

export default function About() {
  return (
    <div className="flex flex-col items-center justify-center min-h-screen p-8 bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300 relative">
      <Link
        href="/"
        className="absolute top-4 left-4 text-gray-400 font-semibold hover:text-gray-200 transition-colors duration-300"
      >
        Go back to Home
      </Link>
      <h1 className="text-5xl font-extrabold mb-8 text-gray-200">About Us</h1>
      <div className="w-full max-w-4xl p-8 bg-gray-800 shadow-xl rounded-lg">
        <div className="flex flex-col lg:flex-row items-center mb-8">
          <Image
            src="/next.svg"
            alt="About Us Image"
            width={400}
            height={300}
            className="rounded-lg shadow-lg mb-4 lg:mb-0 lg:mr-8"
          />
          <p className="text-lg leading-relaxed text-gray-300">
            Welcome to our company! We are committed to providing the best
            services to our customers. Our team is dedicated to excellence and
            innovation. We believe in creating value and making a positive
            impact in the community. Our mission is to deliver outstanding
            experiences and solutions to our clients, driving growth and
            success.
          </p>
        </div>
      </div>
    </div>
  );
}
