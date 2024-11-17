import { Navigate } from 'react-router-dom';

function isAuthenticated() {
  // Example: Check for an access token in localStorage
  const accessToken = localStorage.getItem('access_token');

  // TODO check if the token is not expired
  return !!accessToken; // Return true if the token exists
}

export default function ProtectedRoute({ children }: { children: JSX.Element }) {
	return isAuthenticated() ? children : <Navigate to="/sign-in" replace />;
  }