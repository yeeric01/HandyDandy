import { useQuery } from "@tanstack/react-query";
import { Profile } from "../types"; // <- import from central types file
import api from "../api/api";

export const useProfile = () => {
  return useQuery<Profile, Error>({
    queryKey: ["profile"],
    queryFn: async () => {
      const res = await api.get<Profile>("/profile");
      return res.data;
    },
    staleTime: 1000 * 60,
    retry: 2,
  });
};