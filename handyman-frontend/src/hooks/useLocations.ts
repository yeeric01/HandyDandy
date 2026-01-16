import { useQuery } from "@tanstack/react-query";
import { Location } from "../types";
import api from "../api/api";

export const useLocations = () => {
  return useQuery<Location[], Error>({
    queryKey: ["locations"],
    queryFn: async () => {
      const res = await api.get<Location[]>("/locations");
      return res.data;
    },
    staleTime: 1000 * 60,
    retry: 2,
  });
};