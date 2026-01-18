// Common types
export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface ErrorResponse {
  timestamp: string;
  status: number;
  error: string;
  message: string;
  path: string;
  fieldErrors?: FieldError[];
}

export interface FieldError {
  field: string;
  message: string;
}

// User types
export interface AppUserResponse {
  id: string;
  email: string;
  role: "CUSTOMER" | "HANDYMAN";
}

// Location types
export interface LocationResponse {
  id: string;
  city: string;
  state: string;
  zip: string;
}

export interface LocationSummary {
  id: string;
  city: string;
  state: string;
  zip: string;
}

// Profile types
export interface ProfileResponse {
  id: string;
  name: string;
  email: string;
  role: string;
  userId: string;
  location: LocationSummary | null;
}

export interface ProfileSummary {
  id: string;
  name: string;
  email: string;
}

export interface ProfileCreateRequest {
  name: string;
  email: string;
  userId: string;
  locationId?: string;
}

// Service Category types
export interface ServiceCategoryResponse {
  id: string;
  name: string;
  description: string;
  iconUrl: string | null;
  active: boolean;
}

export interface ServiceCategorySummary {
  id: string;
  name: string;
}

// Handyman Service types
export interface HandymanServiceResponse {
  id: string;
  handyman: ProfileSummary;
  category: ServiceCategorySummary;
  hourlyRate: number;
  yearsExperience: number | null;
  certifications: string | null;
  active: boolean;
}

// Booking types
export type BookingStatus =
  | "PENDING"
  | "CONFIRMED"
  | "IN_PROGRESS"
  | "COMPLETED"
  | "CANCELLED"
  | "NO_SHOW";

export interface BookingResponse {
  id: string;
  customer: ProfileSummary;
  handyman: ProfileSummary;
  category: ServiceCategorySummary;
  location: LocationSummary;
  quoteId: string | null;
  scheduledStart: string;
  scheduledEnd: string;
  description: string;
  status: BookingStatus;
  estimatedCost: number | null;
  finalCost: number | null;
  createdAt: string;
  updatedAt: string | null;
  completedAt: string | null;
}

export interface BookingCreateRequest {
  customerId: string;
  handymanId: string;
  categoryId: string;
  locationId: string;
  quoteId?: string;
  scheduledStart: string;
  scheduledEnd: string;
  description: string;
  estimatedCost?: number;
}

// Review types
export interface ReviewResponse {
  id: string;
  reviewer: ProfileSummary;
  handyman: ProfileSummary;
  bookingId: string;
  rating: number;
  comment: string | null;
  createdAt: string;
}

export interface ReviewStatsResponse {
  handymanId: string;
  averageRating: number | null;
  totalReviews: number;
}

// Quote types
export type QuoteStatus =
  | "PENDING"
  | "ACCEPTED"
  | "REJECTED"
  | "EXPIRED"
  | "WITHDRAWN";

export interface QuoteResponse {
  id: string;
  handyman: ProfileSummary;
  customer: ProfileSummary;
  category: ServiceCategorySummary;
  laborCost: number;
  materialsCost: number | null;
  totalCost: number;
  estimatedHours: number | null;
  description: string | null;
  validUntil: string;
  status: QuoteStatus;
  createdAt: string;
}

// Payment types
export type PaymentStatus =
  | "PENDING"
  | "PROCESSING"
  | "COMPLETED"
  | "FAILED"
  | "REFUNDED";

export interface PaymentResponse {
  id: string;
  bookingId: string;
  payer: ProfileSummary;
  payee: ProfileSummary;
  amount: number;
  paymentMethod: string;
  status: PaymentStatus;
  createdAt: string;
}

// Message types
export interface MessageResponse {
  id: string;
  sender: ProfileSummary;
  receiver: ProfileSummary;
  bookingId: string | null;
  content: string;
  sentAt: string;
  readAt: string | null;
}

// Availability types
export type DayOfWeek =
  | "MONDAY"
  | "TUESDAY"
  | "WEDNESDAY"
  | "THURSDAY"
  | "FRIDAY"
  | "SATURDAY"
  | "SUNDAY";

export interface AvailabilityResponse {
  id: string;
  handymanId: string;
  dayOfWeek: DayOfWeek;
  startTime: string;
  endTime: string;
  available: boolean;
}
