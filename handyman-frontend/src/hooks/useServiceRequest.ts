import { useQuery } from "@tanstack/react-query";
import { ServiceRequest } from "../types";
import api from "../api/api";

export const useServiceRequests = () => {
  return useQuery<ServiceRequest[], Error>({
    queryKey: ["serviceRequests"],
    queryFn: async () => {
      const res = await api.get<ServiceRequest[]>("/servicerequests");
      return res.data;
    },
    staleTime: 1000 * 60, // 1 min
    retry: 2,
  });
};