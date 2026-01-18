import { get, post, put, del } from "./client";
import type { PageResponse, ProfileResponse, ProfileCreateRequest } from "@/types";

export const profilesApi = {
  getAll: (page = 0, size = 20) =>
    get<PageResponse<ProfileResponse>>("/profiles", { page, size }),

  getHandymen: (page = 0, size = 20) =>
    get<PageResponse<ProfileResponse>>("/profiles/handymen", { page, size }),

  getById: (id: string) => get<ProfileResponse>(`/profiles/${id}`),

  create: (data: ProfileCreateRequest) =>
    post<ProfileResponse>("/profiles", data),

  update: (id: string, data: { name?: string; email?: string; locationId?: string }) =>
    put<ProfileResponse>(`/profiles/${id}`, data),

  delete: (id: string) => del(`/profiles/${id}`),
};
