export interface AppUser {
  id: string;
  email: string;
  role: string;
  phone_number?: string;
}

export interface Profile {
  id: string;
  first_name: string;
  last_name: string;
  email: string;
  app_user_id: string;
}

export interface Location {
  id: string;
  name: string;
  address: string;
  profile_id: string;
}

export interface ServiceRequest {
  id: string;
  customer: Profile;
  handyman: Profile;
  location: Location;
  description: string;
  status: "PENDING" | "ACCEPTED" | "COMPLETED" | "CANCELLED";
  createdAt: string; // ISO string from backend
  updatedAt?: string; // optional
}

