import { Routes, Route } from "react-router-dom";
import { MainLayout } from "@/layouts";
import { Dashboard, Bookings, Categories, Handymen, Customers } from "@/pages";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<MainLayout />}>
        <Route index element={<Dashboard />} />
        <Route path="bookings" element={<Bookings />} />
        <Route path="categories" element={<Categories />} />
        <Route path="handymen" element={<Handymen />} />
        <Route path="customers" element={<Customers />} />
      </Route>
    </Routes>
  );
}
