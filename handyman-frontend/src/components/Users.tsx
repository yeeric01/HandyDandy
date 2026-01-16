import React from "react";
import { useUsers } from "../hooks/useUsers";

export const Users: React.FC = () => {
  const { data: users, isLoading, error } = useUsers();

  if (isLoading) return <div>Loading users...</div>;
  if (error) return <div>Error: {error.message}</div>;

  return (
    <div>
      <h2>Users</h2>
      <ul>
        {users?.map((u) => (
          <li key={u.id}>{u.email} ({u.role})</li>
        ))}
      </ul>
    </div>
  );
};