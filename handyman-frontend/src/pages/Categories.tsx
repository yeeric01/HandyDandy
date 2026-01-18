import { useState } from "react";
import { useCategories } from "@/hooks";
import { LoadingSpinner, EmptyState } from "@/components";

export function Categories() {
  const [page, setPage] = useState(0);
  const { data, isLoading, error } = useCategories(page, 12);

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
        <p className="text-sm text-red-700">Failed to load categories.</p>
      </div>
    );
  }

  return (
    <div>
      <div className="sm:flex sm:items-center">
        <div className="sm:flex-auto">
          <h1 className="text-2xl font-bold text-gray-900">Service Categories</h1>
          <p className="mt-2 text-sm text-gray-700">
            Browse all available service categories.
          </p>
        </div>
      </div>

      {data?.content.length === 0 ? (
        <div className="mt-8">
          <EmptyState
            title="No categories"
            description="There are no service categories yet."
          />
        </div>
      ) : (
        <>
          <div className="mt-8 grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {data?.content.map((category) => (
              <div
                key={category.id}
                className="group relative overflow-hidden rounded-lg bg-white shadow transition-shadow hover:shadow-md"
              >
                <div className="aspect-video bg-gradient-to-br from-blue-500 to-blue-600 flex items-center justify-center">
                  {category.iconUrl ? (
                    <img
                      src={category.iconUrl}
                      alt={category.name}
                      className="h-16 w-16 object-contain"
                    />
                  ) : (
                    <span className="text-4xl font-bold text-white/80">
                      {category.name.charAt(0)}
                    </span>
                  )}
                </div>
                <div className="p-4">
                  <div className="flex items-center justify-between">
                    <h3 className="text-lg font-semibold text-gray-900">
                      {category.name}
                    </h3>
                    <span
                      className={`inline-flex rounded-full px-2 py-1 text-xs font-medium ${
                        category.active
                          ? "bg-green-100 text-green-700"
                          : "bg-gray-100 text-gray-700"
                      }`}
                    >
                      {category.active ? "Active" : "Inactive"}
                    </span>
                  </div>
                  {category.description && (
                    <p className="mt-2 text-sm text-gray-500 line-clamp-2">
                      {category.description}
                    </p>
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
