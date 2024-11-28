import { useEffect } from 'react';
import { useNavigate, useSearchParams } from 'react-router-dom';

import { useCheckEmployeeStatus } from 'src/utils/employee';

export default function AuthSuccessPage() {
  const [searchParams] = useSearchParams();
  const navigate = useNavigate();

  const accessToken = searchParams.get('access_token');
  const refreshToken = searchParams.get('refresh_token');

  if (accessToken && refreshToken) {
    // Store tokens in local storage or cookies
    localStorage.setItem('access_token', accessToken);
    localStorage.setItem('refresh_token', refreshToken);
  } else {
    navigate('/sign-in'); // Redirect if tokens are missing
  }

  const { loading, employeeExists, error } = useCheckEmployeeStatus(accessToken);

  useEffect(() => {
    if (loading) return;

    if (error) {
      console.error(error);
      navigate('/error'); // Redirect to an error page if needed
    } else if (employeeExists) {
      navigate('/'); // Redirect to home if employee exists
    } else {
      navigate('/welcome'); // Redirect to welcome if employee does not exist
    }
  }, [loading, employeeExists, error, navigate]);

  return <div>{loading ? 'Authenticating...' : 'Redirecting...'}</div>;
}