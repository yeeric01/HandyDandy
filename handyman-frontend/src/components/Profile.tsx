import React from "react";
import { useProfile } from "../hooks/useProfile";

export const Profile: React.FC = () => {
  const { data: profile, isLoading, error } = useProfile();

  if (isLoading) return <div>Loading profile...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div>
      <h2>Profile</h2>
      <p>{profile?.first_name} {profile?.last_name} - {profile?.email}</p>
    </div>
  );
};