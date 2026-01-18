import { useState } from "react";
import { useBookings } from "@/hooks";
import { LoadingSpinner, EmptyState, StatusBadge } from "@/components";
import { Calendar, MapPin } from "lucide-react";

export function Bookings() {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useBookings(page, 10);

  if (isLoading) {
    return (
      <div className="flex h-64 items-center justify-center">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  if (error) {
    return (
      <div className="rounded-md bg-red-50 p-4">
        <p className="text-sm text-red-700">Failed to load bookings.</p>
      </div>
    );
  }

  return (
    <div>
      <div className="sm:flex sm:items-center">
        <div className="sm:flex-auto">
          <h1 className="text-2xl font-bold text-gray-900">Bookings</h1>
          <p className="mt-2 text-sm text-gray-700">
            Manage all service bookings on the platform.
          </p>
        </div>
      </div>

      {data?.content.length === 0 ? (
        <div className="mt-8">
          <EmptyState
            title="No bookings"
            description="There are no bookings in the system yet."
          />
        </div>
      ) : (
        <>
          <div className="mt-8 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
            {data?.content.map((booking) => (
              <div
                key={booking.id}
                className="overflow-hidden rounded-lg bg-white shadow"
              >
                <div className="p-5">
                  <div className="flex items-center justify-between">
                    <h3 className="text-sm font-medium text-gray-900">
                      {booking.category.name}
                    </h3>
                    <StatusBadge status={booking.status} />
                  </div>

                  <div className="mt-4 space-y-2">
                    <div className="flex items-center text-sm text-gray-500">
                      <Calendar className="mr-2 h-4 w-4" />
                      {new Date(booking.scheduledStart).toLocaleDateString()}
                    </div>
                    <div className="flex items-center text-sm text-gray-500">
                      <MapPin className="mr-2 h-4 w-4" />
                      {booking.location.city}, {booking.location.state}
                    </div>
                  </div>

                  <div className="mt-4 border-t border-gray-200 pt-4">
                    <div className="flex items-center justify-between text-sm">
                      <div>
                        <p className="text-gray-500">Customer</p>
                        <p className="font-medium text-gray-900">
                          {booking.customer.name}
                        </p>
                      </div>
                      <div className="text-right">
                        <p className="text-gray-500">Handyman</p>
                        <p className="font-medium text-gray-900">
                          {booking.handyman.name}
                        </p>
                      </div>
                    </div>
                  </div>

                  {booking.estimatedCost && (
                    <div className="mt-4 text-right">
                      <span className="text-lg font-semibold text-gray-900">
                        ${booking.estimatedCost.toFixed(2)}
                      </span>
                    </div>
                  )}
                </div>
              </div>
            ))}
          </div>

          {data && data.totalPages > 1 && (
            <div className="mt-6 flex items-center justify-between">
              <button
                onClick={() => setPage((p) => Math.max(0, p - 1))}
                disabled={data.first}
                className="rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 disabled:opacity-50"
              >
                Previous
              </button>
              <span className="text-sm text-gray-700">
                Page {data.number + 1} of {data.totalPages}
              </span>
              <button
                onClick={() => setPage((p) => p + 1)}
                disabled={data.last}
                className="rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50 disabled:opacity-50"
              >
                Next
              </button>
            </div>
          )}
        </>
      )}
    </div>
  );
}
