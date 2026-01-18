import { Calendar, Users, Wrench, CheckCircle } from "lucide-react";
import { useBookings } from "@/hooks";
import { useHandymen } from "@/hooks";
import { useActiveCategories } from "@/hooks";
import { LoadingSpinner } from "@/components";

interface StatCardProps {
  name: string;
  value: string | number;
  icon: React.ElementType;
  color: string;
}

function StatCard({ name, value, icon: Icon, color }: StatCardProps) {
  return (
    <div className="overflow-hidden rounded-lg bg-white px-4 py-5 shadow sm:p-6">
      <div className="flex items-center">
        <div className={`flex-shrink-0 rounded-md p-3 ${color}`}>
          <Icon className="h-6 w-6 text-white" aria-hidden="true" />
        </div>
        <div className="ml-5 w-0 flex-1">
          <dt className="truncate text-sm font-medium text-gray-500">{name}</dt>
          <dd className="text-lg font-semibold text-gray-900">{value}</dd>
        </div>
      </div>
    </div>
  );
}

export function Dashboard() {
  const { data: bookingsData, isLoading: bookingsLoading } = useBookings(0, 100);
  const { data: handymenData, isLoading: handymenLoading } = useHandymen(0, 100);
  const { data: categoriesData, isLoading: categoriesLoading } = useActiveCategories(0, 100);

  const isLoading = bookingsLoading || handymenLoading || categoriesLoading;

  const totalBookings = bookingsData?.totalElements ?? 0;
  const completedBookings = bookingsData?.content.filter(
    (b) => b.status === "COMPLETED"
  ).length ?? 0;
  const totalHandymen = handymenData?.totalElements ?? 0;
  const totalCategories = categoriesData?.totalElements ?? 0;

  const stats = [
    {
      name: "Total Bookings",
      value: totalBookings,
      icon: Calendar,
      color: "bg-blue-500",
    },
    {
      name: "Completed Jobs",
      value: completedBookings,
      icon: CheckCircle,
      color: "bg-green-500",
    },
    {
      name: "Active Handymen",
      value: totalHandymen,
      icon: Wrench,
      color: "bg-purple-500",
    },
    {
      name: "Service Categories",
      value: totalCategories,
      icon: Users,
      color: "bg-orange-500",
    },
  ];

  if (isLoading) {
    return (
      <div className="flex h-64 items-center justify-center">
        <LoadingSpinner size="lg" />
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-2xl font-bold text-gray-900">Dashboard</h1>
      <p className="mt-2 text-sm text-gray-700">
        Overview of your handyman marketplace platform.
      </p>

      <div className="mt-8 grid grid-cols-1 gap-5 sm:grid-cols-2 lg:grid-cols-4">
        {stats.map((stat) => (
          <StatCard key={stat.name} {...stat} />
        ))}
      </div>

      <div className="mt-8">
        <h2 className="text-lg font-semibold text-gray-900">Recent Bookings</h2>
        <div className="mt-4 overflow-hidden rounded-lg bg-white shadow">
          {bookingsData?.content.length === 0 ? (
            <p className="p-6 text-center text-gray-500">No bookings yet.</p>
          ) : (
            <table className="min-w-full divide-y divide-gray-200">
              <thead className="bg-gray-50">
                <tr>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                    Customer
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                    Handyman
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                    Service
                  </th>
                  <th className="px-6 py-3 text-left text-xs font-medium uppercase tracking-wider text-gray-500">
                    Status
                  </th>
                </tr>
              </thead>
              <tbody className="divide-y divide-gray-200 bg-white">
                {bookingsData?.content.slice(0, 5).map((booking) => (
                  <tr key={booking.id}>
                    <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-900">
                      {booking.customer.name}
                    </td>
                    <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-900">
                      {booking.handyman.name}
                    </td>
                    <td className="whitespace-nowrap px-6 py-4 text-sm text-gray-500">
                      {booking.category.name}
                    </td>
                    <td className="whitespace-nowrap px-6 py-4 text-sm">
                      <span
                        className={`inline-flex rounded-full px-2 text-xs font-semibold leading-5 ${
                          booking.status === "COMPLETED"
                            ? "bg-green-100 text-green-800"
                            : booking.status === "PENDING"
                            ? "bg-yellow-100 text-yellow-800"
                            : "bg-blue-100 text-blue-800"
                        }`}
                      >
                        {booking.status}
                      </span>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          )}
        </div>
      </div>
    </div>
  );
}
