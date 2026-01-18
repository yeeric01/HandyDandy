import { NavLink } from "react-router-dom";
import {
  Home,
  Calendar,
  Users,
  FolderOpen,
  X,
  Wrench,
} from "lucide-react";

const navigation = [
  { name: "Dashboard", href: "/", icon: Home },
  { name: "Bookings", href: "/bookings", icon: Calendar },
  { name: "Handymen", href: "/handymen", icon: Wrench },
  { name: "Categories", href: "/categories", icon: FolderOpen },
  { name: "Customers", href: "/customers", icon: Users },
];

interface SidebarProps {
  open: boolean;
  onClose: () => void;
}

function classNames(...classes: string[]) {
  return classes.filter(Boolean).join(" ");
}

export function Sidebar({ open, onClose }: SidebarProps) {
  const sidebarContent = (
    <nav className="flex flex-1 flex-col">
      <ul role="list" className="flex flex-1 flex-col gap-y-7">
        <li>
          <ul role="list" className="-mx-2 space-y-1">
            {navigation.map((item) => (
              <li key={item.name}>
                <NavLink
                  to={item.href}
                  className={({ isActive }) =>
                    classNames(
                      isActive
                        ? "bg-blue-50 text-blue-600"
                        : "text-gray-700 hover:bg-gray-50 hover:text-blue-600",
                      "group flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6"
                    )
                  }
                  onClick={onClose}
                >
                  {({ isActive }) => (
                    <>
                      <item.icon
                        className={classNames(
                          isActive
                            ? "text-blue-600"
                            : "text-gray-400 group-hover:text-blue-600",
                          "h-6 w-6 shrink-0"
                        )}
                        aria-hidden="true"
                      />
                      {item.name}
                    </>
                  )}
                </NavLink>
              </li>
            ))}
          </ul>
        </li>
      </ul>
    </nav>
  );

  return (
    <>
      {/* Mobile sidebar */}
      <div
        className={classNames(
          "relative z-50 lg:hidden",
          open ? "" : "hidden"
        )}
        role="dialog"
        aria-modal="true"
      >
        <div
          className="fixed inset-0 bg-gray-900/80"
          onClick={onClose}
        />

        <div className="fixed inset-0 flex">
          <div className="relative mr-16 flex w-full max-w-xs flex-1">
            <div className="absolute left-full top-0 flex w-16 justify-center pt-5">
              <button
                type="button"
                className="-m-2.5 p-2.5"
                onClick={onClose}
              >
                <span className="sr-only">Close sidebar</span>
                <X className="h-6 w-6 text-white" aria-hidden="true" />
              </button>
            </div>

            <div className="flex grow flex-col gap-y-5 overflow-y-auto bg-white px-6 pb-4">
              <div className="flex h-16 shrink-0 items-center">
                <span className="text-xl font-bold text-blue-600">
                  HandyDandy
                </span>
              </div>
              {sidebarContent}
            </div>
          </div>
        </div>
      </div>

      {/* Desktop sidebar */}
      <div className="hidden lg:fixed lg:inset-y-0 lg:z-50 lg:flex lg:w-72 lg:flex-col">
        <div className="flex grow flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white px-6 pb-4">
          <div className="flex h-16 shrink-0 items-center">
            <span className="text-xl font-bold text-blue-600">
              HandyDandy
            </span>
          </div>
          {sidebarContent}
        </div>
      </div>
    </>
  );
}
