import type { BookingStatus } from "@/types";

interface StatusBadgeProps {
  status: BookingStatus;
}

const statusStyles: Record<BookingStatus, { bg: string; text: string }> = {
  PENDING: { bg: "bg-yellow-50", text: "text-yellow-700" },
  CONFIRMED: { bg: "bg-blue-50", text: "text-blue-700" },
  IN_PROGRESS: { bg: "bg-purple-50", text: "text-purple-700" },
  COMPLETED: { bg: "bg-green-50", text: "text-green-700" },
  CANCELLED: { bg: "bg-red-50", text: "text-red-700" },
  NO_SHOW: { bg: "bg-gray-50", text: "text-gray-700" },
};

const statusLabels: Record<BookingStatus, string> = {
  PENDING: "Pending",
  CONFIRMED: "Confirmed",
  IN_PROGRESS: "In Progress",
  COMPLETED: "Completed",
  CANCELLED: "Cancelled",
  NO_SHOW: "No Show",
};

export function StatusBadge({ status }: StatusBadgeProps) {
  const { bg, text } = statusStyles[status];

  return (
    <span
      className={`inline-flex items-center rounded-md px-2 py-1 text-xs font-medium ${bg} ${text}`}
    >
      {statusLabels[status]}
    </span>
  );
}
