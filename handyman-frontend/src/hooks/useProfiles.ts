import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { profilesApi } from "@/api";
import type { ProfileResponse } from "@/types";

export const profileKeys = {
  all: ["profiles"] as const,
  lists: () => [...profileKeys.all, "list"] as const,
  list: (page: number, size: number) =>
    [...profileKeys.lists(), { page, size }] as const,
  handymen: (page: number, size: number) =>
    [...profileKeys.all, "handymen", { page, size }] as const,
  details: () => [...profileKeys.all, "detail"] as const,
  detail: (id: string) => [...profileKeys.details(), id] as const,
};

export function useProfiles(page = 0, size = 20) {
  return useQuery({
    queryKey: profileKeys.list(page, size),
    queryFn: () => profilesApi.getAll(page, size),
  });
}

export function useHandymen(page = 0, size = 20) {
  return useQuery({
    queryKey: profileKeys.handymen(page, size),
    queryFn: () => profilesApi.getHandymen(page, size),
  });
}

export function useProfile(id: string) {
  return useQuery({
    queryKey: profileKeys.detail(id),
    queryFn: () => profilesApi.getById(id),
    enabled: !!id,
  });
}

export function useCreateProfile() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: profilesApi.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: profileKeys.all });
    },
  });
}

export function useUpdateProfile() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      id,
      data,
    }: {
      id: string;
      data: Parameters<typeof profilesApi.update>[1];
    }) => profilesApi.update(id, data),
    onSuccess: (data: ProfileResponse) => {
      queryClient.invalidateQueries({ queryKey: profileKeys.all });
      queryClient.setQueryData(profileKeys.detail(data.id), data);
    },
  });
}

export function useDeleteProfile() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: profilesApi.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: profileKeys.all });
    },
  });
}
