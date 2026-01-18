import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { bookingsApi } from "@/api";
import type { BookingResponse, BookingStatus } from "@/types";

export const bookingKeys = {
  all: ["bookings"] as const,
  lists: () => [...bookingKeys.all, "list"] as const,
  list: (page: number, size: number) =>
    [...bookingKeys.lists(), { page, size }] as const,
  byCustomer: (customerId: string, page: number, size: number) =>
    [...bookingKeys.all, "customer", customerId, { page, size }] as const,
  byHandyman: (handymanId: string, page: number, size: number) =>
    [...bookingKeys.all, "handyman", handymanId, { page, size }] as const,
  byStatus: (status: BookingStatus, page: number, size: number) =>
    [...bookingKeys.all, "status", status, { page, size }] as const,
  details: () => [...bookingKeys.all, "detail"] as const,
  detail: (id: string) => [...bookingKeys.details(), id] as const,
};

export function useBookings(page = 0, size = 20) {
  return useQuery({
    queryKey: bookingKeys.list(page, size),
    queryFn: () => bookingsApi.getAll(page, size),
  });
}

export function useBookingsByCustomer(
  customerId: string,
  page = 0,
  size = 20
) {
  return useQuery({
    queryKey: bookingKeys.byCustomer(customerId, page, size),
    queryFn: () => bookingsApi.getByCustomer(customerId, page, size),
    enabled: !!customerId,
  });
}

export function useBookingsByHandyman(
  handymanId: string,
  page = 0,
  size = 20
) {
  return useQuery({
    queryKey: bookingKeys.byHandyman(handymanId, page, size),
    queryFn: () => bookingsApi.getByHandyman(handymanId, page, size),
    enabled: !!handymanId,
  });
}

export function useBookingsByStatus(
  status: BookingStatus,
  page = 0,
  size = 20
) {
  return useQuery({
    queryKey: bookingKeys.byStatus(status, page, size),
    queryFn: () => bookingsApi.getByStatus(status, page, size),
  });
}

export function useBooking(id: string) {
  return useQuery({
    queryKey: bookingKeys.detail(id),
    queryFn: () => bookingsApi.getById(id),
    enabled: !!id,
  });
}

export function useCreateBooking() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: bookingsApi.create,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: bookingKeys.all });
    },
  });
}

export function useUpdateBooking() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: ({
      id,
      data,
    }: {
      id: string;
      data: Parameters<typeof bookingsApi.update>[1];
    }) => bookingsApi.update(id, data),
    onSuccess: (data: BookingResponse) => {
      queryClient.invalidateQueries({ queryKey: bookingKeys.all });
      queryClient.setQueryData(bookingKeys.detail(data.id), data);
    },
  });
}

export function useDeleteBooking() {
  const queryClient = useQueryClient();

  return useMutation({
    mutationFn: bookingsApi.delete,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: bookingKeys.all });
    },
  });
}
