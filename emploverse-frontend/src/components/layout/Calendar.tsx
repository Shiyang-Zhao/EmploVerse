import React, { useState, useEffect, useRef } from "react";
import { gsap } from "gsap";

interface CalendarProps {
  employeeId: number;
}

const Calendar: React.FC<CalendarProps> = ({ employeeId }) => {
  const [currentDate, setCurrentDate] = useState(new Date());
  const [daysWorked, setDaysWorked] = useState<boolean[]>([]);
  const daysRef = useRef<HTMLDivElement[]>([]);

  useEffect(() => {
    const daysInMonth = new Date(
      currentDate.getFullYear(),
      currentDate.getMonth() + 1,
      0
    ).getDate();
    const attendance = Array.from(
      { length: daysInMonth },
      () => Math.random() > 0.3
    );
    setDaysWorked(attendance);
  }, [currentDate]);

  useEffect(() => {
    if (daysRef.current.length) {
      gsap.from(daysRef.current, {
        duration: 1,
        opacity: 0,
        stagger: 0.05,
        ease: "power3.out",
      });
    }
  }, [daysWorked]);

  const renderDay = (worked: boolean, day: number, index: number) => {
    return (
      <div
        ref={(el) => {
          if (el) daysRef.current[index] = el; // Only assign if el is not null
        }}
        className="flex justify-center items-center w-full h-12 border border-gray-600 bg-gray-700 hover:bg-gray-600"
        key={index}
      >
        <div className="text-center">
          <div className="text-sm text-gray-300">{day}</div>
          <div className="text-lg">
            {worked ? (
              <span className="text-green-500">&#10003;</span> // Green check mark
            ) : (
              <span className="text-red-500">&#10007;</span> // Red X mark
            )}
          </div>
        </div>
      </div>
    );
  };

  const daysHeader = ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"].map(
    (day) => (
      <div
        key={day}
        className="w-full h-12 flex justify-center items-center border border-gray-600 bg-gray-800 text-gray-300"
      >
        {day}
      </div>
    )
  );

  const calendarDays = daysWorked.map((worked, index) =>
    renderDay(worked, index + 1, index)
  );

  return (
    <div className="p-5 bg-gray-800 mt-5 rounded shadow text-gray-300">
      <h2 className="text-lg leading-6 font-medium text-gray-200">
        Attendance Calendar
      </h2>
      <div className="text-center text-lg my-2 text-gray-200">
        {currentDate.toLocaleDateString("en-us", {
          month: "long",
          year: "numeric",
        })}
      </div>
      <div className="grid grid-cols-7 gap-1 mt-3">
        {daysHeader}
        {calendarDays}
      </div>
    </div>
  );
};

export default Calendar;
