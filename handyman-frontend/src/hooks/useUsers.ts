import { useQuery } from "@tanstack/react-query";
import { AppUser } from "../types";
import api from "../api/api";

export const useUsers = () => {
  return useQuery<AppUser[], Error>({
    queryKey: ["users"],
    queryFn: async () => {
      const res = await api.get<AppUser[]>("/users");
      return res.data;
    },
    staleTime: 1000 * 60, // 1 min caching
    retry: 2,
  });
};