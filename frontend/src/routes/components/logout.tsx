import { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

export function LogoutPage() {
  const navigate = useNavigate();

  useEffect(() => {
    // Clear tokens from local storage or cookies
    localStorage.removeItem('access_token');
    localStorage.removeItem('refresh_token');

    // (Optional) Notify backend about logout
    // await axios.post('/api/auth/logout');

    // Redirect to sign-in page
    navigate('/sign-in');
  }, [navigate]);

  return <div>Logging out...</div>; // Optionally show a message or spinner
}