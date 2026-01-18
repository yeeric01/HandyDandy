import { get, post, put, del } from "./client";
import type {
  PageResponse,
  BookingResponse,
  BookingCreateRequest,
  BookingStatus,
} from "@/types";

export const bookingsApi = {
  getAll: (page = 0, size = 20) =>
    get<PageResponse<BookingResponse>>("/bookings", { page, size }),

  getByCustomer: (customerId: string, page = 0, size = 20) =>
    get<PageResponse<BookingResponse>>(`/bookings/customer/${customerId}`, {
      page,
      size,
    }),

  getByHandyman: (handymanId: string, page = 0, size = 20) =>
    get<PageResponse<BookingResponse>>(`/bookings/handyman/${handymanId}`, {
      page,
      size,
    }),

  getByStatus: (status: BookingStatus, page = 0, size = 20) =>
    get<PageResponse<BookingResponse>>(`/bookings/status/${status}`, {
      page,
      size,
    }),

  getById: (id: string) => get<BookingResponse>(`/bookings/${id}`),

  create: (data: BookingCreateRequest) =>
    post<BookingResponse>("/bookings", data),

  update: (
    id: string,
    data: {
      status?: BookingStatus;
      description?: string;
      finalCost?: number;
    }
  ) => put<BookingResponse>(`/bookings/${id}`, data),

  delete: (id: string) => del(`/bookings/${id}`),
};
