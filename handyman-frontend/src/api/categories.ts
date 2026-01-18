import { get, post, put, del } from "./client";
import type { PageResponse, ServiceCategoryResponse } from "@/types";

export const categoriesApi = {
  getAll: (page = 0, size = 20) =>
    get<PageResponse<ServiceCategoryResponse>>("/categories", { page, size }),

  getActive: (page = 0, size = 20) =>
    get<PageResponse<ServiceCategoryResponse>>("/categories/active", {
      page,
      size,
    }),

  getById: (id: string) => get<ServiceCategoryResponse>(`/categories/${id}`),

  create: (data: { name: string; description?: string; iconUrl?: string }) =>
    post<ServiceCategoryResponse>("/categories", data),

  update: (
    id: string,
    data: { name?: string; description?: string; active?: boolean }
  ) => put<ServiceCategoryResponse>(`/categories/${id}`, data),

  delete: (id: string) => del(`/categories/${id}`),
};
