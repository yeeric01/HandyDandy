import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { categoriesApi } from "@/api";
import type { ServiceCategoryResponse } from "@/types";

export const categoryKeys = {
  all: ["categories"] as const,
  lists: () => [...categoryKeys.all, "list"] as const,
  list: (page: number, size: number) =>
    [...categoryKeys.lists(), { page, size }] as const,
  active: (page: number, size: number) =>
    [...categoryKeys.all, "active", { page, size }] as const,
  details: () => [...categoryKeys.all, "detail"] as const,
  detail: (id: string) => [...categoryKeys.details(), id] as const,
};

export function useCategories(page = 0, size = 20) {
  return useQuery({
    queryKey: categoryKeys.list(page, size),
    queryFn: () => categoriesApi.getAll(page, size),
  });
}

export function useActiveCategories(page = 0, size = 20) {
  return useQuery({
    queryKey: categoryKeys.active(page, size),
    queryFn: () => categoriesApi.getActive(page, size),
  });
}

export function useCategory(id: string) {
  return useQuery({
    queryKey: categoryKeys.detail(id),
    queryFn: () => categoriesApi.getById(id),
    enabled: !!id,
  });
}

export function useCreateCategory() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: categoriesApi.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: categoryKeys.all });
    },
  });
}

export function useUpdateCategory() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      id,
      data,
    }: {
      id: string;
      data: Parameters<typeof categoriesApi.update>[1];
    }) => categoriesApi.update(id, data),
    onSuccess: (data: ServiceCategoryResponse) => {
      queryClient.invalidateQueries({ queryKey: categoryKeys.all });
      queryClient.setQueryData(categoryKeys.detail(data.id), data);
    },
  });
}

export function useDeleteCategory() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: categoriesApi.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: categoryKeys.all });
    },
  });
}
