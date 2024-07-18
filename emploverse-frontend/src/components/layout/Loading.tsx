import React from "react";

const Loading: React.FC = () => {
  return (
    <div className="flex items-center justify-center w-full h-full bg-gradient-to-b from-gray-700 via-gray-800 to-gray-900 text-gray-300">
      <div className="flex items-center justify-center w-16 h-16">
        {[...Array(12)].map((_, i) => (
          <div
            key={i}
            className="absolute w-3 h-3 bg-gray-300 rounded-full"
            style={{
              transform: `rotate(${i * 30}deg) translate(24px)`,
              animation: `fade 1.2s linear infinite`,
              animationDelay: `${i * -0.1}s`,
              opacity: i / 12,
            }}
          ></div>
        ))}
      </div>
      <style jsx>{`
        @keyframes fade {
          0%,
          39%,
          100% {
            opacity: 0.3;
          }
          40% {
            opacity: 1;
          }
        }
      `}</style>
    </div>
  );
};

export default Loading;
